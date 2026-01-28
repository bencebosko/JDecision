package com.bbsoft.jdecision;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class DecisionTreeNodeMinEntropy extends DecisionTreeNode {

    DecisionTreeNodeMinEntropy(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature) {
        this(records, remainingFeatures, targetFeature, null);
    }

    DecisionTreeNodeMinEntropy(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature, FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, featureClass);
    }

    /* Finds min entropy split at specific node of the tree. */
    @Override
    protected Map<FeatureClass<?>, List<Record>> split() {
        if (remainingFeatures.isEmpty()) {
            splittingFeature = targetFeature;
            return createClassification(splittingFeature, records);
        } else {
            Map<FeatureClass<?>, List<Record>> optimalClassification = Collections.emptyMap();
            var minEntropy = Double.MAX_VALUE;
            for (Feature<Object> feature : remainingFeatures) {
                final var classification = createClassification(feature, records);
                final var entropy = getEntropy(classification);
                if (entropy < minEntropy) {
                    splittingFeature = feature;
                    optimalClassification = classification;
                    minEntropy = entropy;
                }
            }
            if (Objects.nonNull(splittingFeature)) {
                remainingFeatures.remove(splittingFeature);
            }
            return optimalClassification;
        }
    }

    private double getEntropy(Map<FeatureClass<?>, List<Record>> classification) {
        var entropy = 0.0;
        for (Map.Entry<FeatureClass<?>, List<Record>> entry : classification.entrySet()) {
            var p = entry.getKey().getProbability();
            entropy += p * (Math.log(p) / Math.log(2));
        }
        return -1 * entropy;
    }
}
