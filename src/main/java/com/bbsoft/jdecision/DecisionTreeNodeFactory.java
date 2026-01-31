package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DecisionTreeNodeFactory {

    private final DecisionMode decisionMode;

    DecisionTreeNode getRootNode(List<Record> records, List<Feature<Object>> features, TargetFeature<Object> targetFeature) {
        switch (decisionMode) {
            case REGRESSION:
                return null;
            case MANUAL_CLASSIFICATION:
                return DecisionTreeNodeManual.builder()
                    .records(records)
                    .remainingFeatures(new ArrayList<>(features))
                    .targetFeature(targetFeature)
                    .isRegression(false)
                    .build();
            default:
                return DecisionTreeNodeMinEntropy.builder()
                    .records(records)
                    .remainingFeatures(new ArrayList<>(features))
                    .targetFeature(targetFeature)
                    .isRegression(false)
                    .build();
        }
    }

    DecisionTreeNode getChildNode(List<Record> records, List<Feature<Object>> remainingFeatures, TargetFeature<Object> targetFeature, FeatureClass<?> featureClass) {
        switch (decisionMode) {
            case REGRESSION:
                return null;
            case MANUAL_CLASSIFICATION:
                return DecisionTreeNodeManual.builder()
                    .records(records)
                    .remainingFeatures(remainingFeatures)
                    .targetFeature(targetFeature)
                    .isRegression(false)
                    .featureClass(featureClass)
                    .build();
            default:
                return DecisionTreeNodeMinEntropy.builder()
                    .records(records)
                    .remainingFeatures(remainingFeatures)
                    .targetFeature(targetFeature)
                    .isRegression(false)
                    .featureClass(featureClass)
                    .build();
        }
    }
}
