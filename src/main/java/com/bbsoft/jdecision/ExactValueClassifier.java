package com.bbsoft.jdecision;

class ExactValueClassifier<T> extends Classifier<T> {

    ExactValueClassifier(Feature<T> feature) {
        super(feature);
    }

    /* Maps a feature value to a single value class. */
    @Override
    protected FeatureClass<T> valueMapper(T value) {
        return new FeatureClass<>(feature, value, null);
    }
}
