package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class DecisionTreeNodeManual extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeManual(List<Record> records,
                           List<Feature<Object>> remainingFeatures,
                           TargetFeature<Object> targetFeature,
                           boolean isRegression,
                           FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, isRegression, featureClass);
    }

    /* Splits the node with the next feature in manual order. */
    @Override
    protected Optional<Map<FeatureClass<?>, List<Record>>> split() {
        if (remainingFeatures.isEmpty()) {
            return Optional.empty();
        } else {
            splittingFeature = remainingFeatures.remove(0);
        }
        return Optional.of(createClassification(splittingFeature, records).getClassification());
    }
}
