package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DecisionTreeNodeFactory {

    private final SplitMode splitMode;

    DecisionTreeNode getRootNode(List<Record> records, List<Feature<Object>> features, TargetFeature<Object> targetFeature) {
        final var featuresCopy = new ArrayList<>(features);
        if (splitMode == SplitMode.MANUAL) {
            return new DecisionTreeNodeManual(records, featuresCopy, targetFeature);
        }
        return new DecisionTreeNodeMinEntropy(records, featuresCopy, targetFeature);
    }

    DecisionTreeNode getChildNode(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature, FeatureClass<?> featureClass) {
        if (splitMode == SplitMode.MANUAL) {
            return new DecisionTreeNodeManual(records, remainingFeatures, targetFeature, featureClass);
        }
        return new DecisionTreeNodeMinEntropy(records, remainingFeatures, targetFeature, featureClass);
    }
}
