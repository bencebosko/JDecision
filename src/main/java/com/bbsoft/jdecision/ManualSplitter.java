package com.bbsoft.jdecision;

import java.util.List;
import java.util.Map;

class ManualSplitter extends DecisionTreeNode {

    ManualSplitter(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature) {
        this(records, remainingFeatures, targetFeature, null);
    }

    ManualSplitter(List<Record> records, List<Feature<Object>> remainingFeatures, Feature<Object> targetFeature, FeatureClass<?> featureClass) {
        super(records, remainingFeatures, targetFeature, featureClass);
    }

    /* Splits records in order of the given Features. */
    @Override
    protected Map<FeatureClass<?>, List<Record>> doSplit() {
        final var nextFeature = remainingFeatures.remove(0);
        splittingFeature = nextFeature;
        return createClassification(nextFeature, records);
    }
}
