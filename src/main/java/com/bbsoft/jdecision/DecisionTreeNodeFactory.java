package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DecisionTreeNodeFactory {

    private final SplitMode splitMode;

    DecisionTreeNode getRootNode(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature) {
        if (splitMode == SplitMode.MANUAL) {
            return new ManualSplitter(records, remainingFeatures, targetFeature);
        }
        return new MinEntropySplitter(records, remainingFeatures, targetFeature);
    }

    DecisionTreeNode getChildNode(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature, FeatureClass<?> featureClass) {
        if (splitMode == SplitMode.MANUAL) {
            return new ManualSplitter(records, remainingFeatures, targetFeature, featureClass);
        }
        return new MinEntropySplitter(records, remainingFeatures, targetFeature, featureClass);
    }
}
