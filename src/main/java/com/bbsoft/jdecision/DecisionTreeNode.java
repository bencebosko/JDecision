package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract class DecisionTreeNode {

    protected final List<Record> records;
    @Getter(AccessLevel.PACKAGE)
    protected final List<Feature<Object>> remainingFeatures;
    protected final TargetFeature<Object> targetFeature;
    private final boolean isRegression;
    /* Set after splitting the parent. Null for the root node. */
    private FeatureClass<?> featureClass;
    /* Set after splitting the node. Null for leaf nodes. */
    @Getter(AccessLevel.PACKAGE)
    protected Feature<Object> splittingFeature;
    /* Set after splitting the node. Null for leaf nodes. */
    private List<DecisionTreeNode> children;

    protected DecisionTreeNode(List<Record> records,
                               List<Feature<Object>> remainingFeatures,
                               TargetFeature<Object> targetFeature,
                               boolean isRegression,
                               FeatureClass<?> featureClass) {
        this.records = Collections.unmodifiableList(records);
        this.remainingFeatures = remainingFeatures;
        this.targetFeature = targetFeature;
        this.isRegression = isRegression;
        this.featureClass = featureClass;
    }

    /* Returns null for Leaf nodes. */
    protected abstract Optional<Map<FeatureClass<?>, List<Record>>> split();

    protected Map<FeatureClass<?>, List<Record>> createClassification(Feature<Object> feature, List<Record> records) {
        final Map<FeatureClass<?>, List<Record>> classification = new HashMap<>();
        for (int rec = 0; rec < records.size(); rec++) {
            final Record record = records.get(rec);
            final FeatureClass<?> featureClass = feature.getClassifier().classify(record, feature);
            featureClass.addRecord(record, records.size(), targetFeature, isRegression);
            var recordsOfClass = classification.computeIfAbsent(featureClass, key -> new ArrayList<>());
            recordsOfClass.add(record);
        }
        return Collections.unmodifiableMap(classification);
    }

    void setChildren(List<DecisionTreeNode> children) {
        this.children = Collections.unmodifiableList(children);
    }
}
