package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

abstract class DecisionTreeNode {

    protected final List<Record> records;
    @Getter(AccessLevel.PACKAGE)
    protected final List<Feature<Object>> remainingFeatures;
    protected final Feature<Object> targetFeature;
    /* Set after splitting the parent. Null for the root node. */
    private FeatureClass<?> featureClass;
    /* Set after splitting the node. Null for leaf nodes. */
    @Getter(AccessLevel.PACKAGE)
    protected Feature<Object> splittingFeature;
    /* Set after splitting the node. Null for leaf nodes. */
    private List<DecisionTreeNode> children;

    protected DecisionTreeNode(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature, FeatureClass<?> featureClass) {
        this.records = Collections.unmodifiableList(records);
        this.remainingFeatures = remainingFeatures;
        this.targetFeature = targetFeature;
        this.featureClass = featureClass;
    }

    protected abstract Map<FeatureClass<?>, List<Record>> split();

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
        final Map<FeatureClass<?>, List<Record>> classification = new HashMap<>();
        records.forEach(record -> {
            var featureValue = record.getValue(feature);
            if (Objects.isNull(featureValue)) {
                throw new DecisionTreeException("Record has missing feature: " + feature.getName());
            }
            var featureClass = feature.getClassifier().classify(featureValue);
            if (Objects.isNull(featureClass)) {
                throw new DecisionTreeException("No class found for feature '" + feature.getName() + "' with value: " + featureValue);
            }
            List<Record> recordsOfClass = classification.computeIfAbsent(featureClass, key -> new ArrayList<>());
            recordsOfClass.add(record);
            featureClass.setProbability(getProbability(recordsOfClass.size(), records.size()));
        });
        return Collections.unmodifiableMap(classification);
    }

    private double getProbability(int count, int totalCount) {
        return ((double) count / totalCount);
    }
}
