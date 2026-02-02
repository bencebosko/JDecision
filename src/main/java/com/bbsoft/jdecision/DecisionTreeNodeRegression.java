package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

class DecisionTreeNodeRegression extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeRegression(List<Record> records,
                               List<Feature<Object>> remainingFeatures,
                               TargetFeature<Object> targetFeature,
                               boolean isRegression,
                               FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, isRegression, featureClass);
    }

    /* Finds min squared error split at specific node of the tree. */
    @Override
    protected Optional<Map<FeatureClass<?>, List<Record>>> split() {
        if (remainingFeatures.isEmpty()) {
            return Optional.empty();
        } else {
            Map<FeatureClass<?>, List<Record>> optimalClassification = Collections.emptyMap();
            var minSquaredError = Double.MAX_VALUE;
            for (Feature<Object> feature : remainingFeatures) {
                final var classificationDTO = createClassification(feature, records);
                final var squaredError = getSquaredError(classificationDTO);
                if (squaredError < minSquaredError) {
                    splittingFeature = feature;
                    optimalClassification = classificationDTO.getClassification();
                    minSquaredError = squaredError;
                }
            }
            if (Objects.nonNull(splittingFeature)) {
                remainingFeatures.remove(splittingFeature);
            }
            return Optional.of(optimalClassification);
        }
    }

    private double getSquaredError(ClassificationDTO classification) {
        final var classificationMean = classification.getClassificationMean();
        var squaredError = 0.0;
        for (FeatureClass<?> cls : classification.getClassification().keySet()) {
            var error = Math.abs(classificationMean - cls.getRegressionAggregate().getMeanValue());
            squaredError += error * error;
        }
        return squaredError;
    }
}
