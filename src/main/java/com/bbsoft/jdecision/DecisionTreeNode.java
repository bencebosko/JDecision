package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

abstract class DecisionTreeNode {

    protected final List<Record> records;
    @Getter(AccessLevel.PACKAGE)
    protected final List<Feature<Object>> remainingFeatures;
    protected final TargetFeature<Object> targetFeature;
    /* Set after splitting the parent. Null for the root node. */
    private FeatureClass<?> featureClass;
    /* Set after splitting the node. Null for leaf nodes. */
    @Getter(AccessLevel.PACKAGE)
    protected Feature<Object> splittingFeature;
    /* Set after splitting the node. Null for leaf nodes. */
    private List<DecisionTreeNode> children;

    protected DecisionTreeNode(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature, FeatureClass<?> featureClass) {
        this.records = Collections.unmodifiableList(records);
        this.remainingFeatures = remainingFeatures;
        this.targetFeature = targetFeature;
        this.featureClass = featureClass;
    }

    /* Returns null for Leaf nodes. */
    protected abstract Optional<Map<FeatureClass<?>, List<Record>>> split();

    boolean hasLeafChildren() {
        return Objects.equals(splittingFeature, targetFeature);
    }

    void setChildren(List<DecisionTreeNode> children) {
        this.children = Collections.unmodifiableList(children);
    }

    /*
     Classifies a list of records.
     If a Record has missing feature or cannot be classified DecisionTreeException is thrown.
     */
    protected Map<FeatureClass<?>, List<Record>> createClassification(Feature<Object> feature, List<Record> records) {
        final var totalCount = records.size();
        final Map<FeatureClass<?>, List<Record>> classification = new HashMap<>();
        records.forEach(record -> {
            final var featureValue = record.getValue(feature);
            final FeatureClass<?> featureClass = feature.getClassifier().classify(featureValue);
            final var recordsOfClass = classification.computeIfAbsent(featureClass, key -> new ArrayList<>());
            recordsOfClass.add(record);
            featureClass.setCount(recordsOfClass.size());
            featureClass.setProbability(getProbability(recordsOfClass.size(), totalCount));
            addTargetClass(featureClass, record);
        });
        return Collections.unmodifiableMap(classification);
    }

    private void addTargetClass(FeatureClass<?> featureClass, Record record) {
        var targetValue = record.getValue(targetFeature);
        FeatureClass<?> targetClass = featureClass.computeTargetClass(targetFeature.getClassifier().classify(targetValue));
        targetClass.setCount(targetClass.getCount() + 1);
        targetClass.setProbability(getProbability(targetClass.getCount(), featureClass.getCount()));
    }

    private double getProbability(int count, int totalCount) {
        return ((double) count / totalCount);
    }
}
