package com.bbsoft.jdecision;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class DecisionTreeNodeManual extends DecisionTreeNode {

    DecisionTreeNodeManual(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature) {
        this(records, remainingFeatures, targetFeature, null);
    }

    DecisionTreeNodeManual(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature, FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, featureClass);
    }

    /* Splits records with the given features one by one in sequential order. */
    @Override
    protected Optional<Map<FeatureClass<?>, List<Record>>> split() {
        if (remainingFeatures.isEmpty()) {
            return Optional.empty();
        } else {
            splittingFeature = remainingFeatures.remove(0);
        }
        return Optional.of(createClassification(splittingFeature, records));
    }
}
