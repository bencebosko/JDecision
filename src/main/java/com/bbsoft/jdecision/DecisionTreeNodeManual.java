package com.bbsoft.jdecision;

import java.util.List;
import java.util.Map;

class DecisionTreeNodeManual extends DecisionTreeNode {

    DecisionTreeNodeManual(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature) {
        this(records, remainingFeatures, targetFeature, null);
    }

    DecisionTreeNodeManual(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature, FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, featureClass);
    }

    /* Splits records with the given features one by one in sequential order. */
    @Override
    protected Map<FeatureClass<?>, List<Record>> split() {
        if (remainingFeatures.isEmpty()) {
            splittingFeature = targetFeature;
        } else {
            splittingFeature = remainingFeatures.remove(0);
        }
        return createClassification(splittingFeature, records);
    }
}
