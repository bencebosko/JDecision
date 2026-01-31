package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

class DecisionTreeNodeMinEntropy extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeMinEntropy(List<Record> records,
                               List<Feature<Object>> remainingFeatures,
                               TargetFeature<Object> targetFeature,
                               boolean isRegression,
                               FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, isRegression, featureClass);
    }

    /* Finds min entropy split at specific node of the tree. */
    @Override
    protected Optional<Map<FeatureClass<?>, List<Record>>> split() {
        if (remainingFeatures.isEmpty()) {
            return Optional.empty();
        } else {
            Map<FeatureClass<?>, List<Record>> optimalClassification = Collections.emptyMap();
            var minEntropy = Double.MAX_VALUE;
            for (Feature<Object> feature : remainingFeatures) {
                final var classification = createClassification(feature, records);
                final var entropy = getAverageEntropy(classification);
                if (entropy < minEntropy) {
                    splittingFeature = feature;
                    optimalClassification = classification;
                    minEntropy = entropy;
                }
            }
            if (Objects.nonNull(splittingFeature)) {
                remainingFeatures.remove(splittingFeature);
            }
            return Optional.of(optimalClassification);
        }
    }

    private double getAverageEntropy(Map<FeatureClass<?>, List<Record>> classification) {
        final var classCount = classification.size();
        var entropy = 0.0;
        for (FeatureClass<?> featureClass : classification.keySet()) {
            var entropyOfClass = 0.0;
            for (FeatureClass<?> targetClass : featureClass.getTargetClasses().keySet()) {
                var p = targetClass.getProbability();
                entropyOfClass += p * (Math.log(p) / Math.log(2));
            }
            entropy += entropyOfClass;
        }
        return (-1 * entropy) / classCount;
    }
}
