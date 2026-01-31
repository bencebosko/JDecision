package com.bbsoft.jdecision;

/* Classifies a value to a single class. */
class ExactValueClassifier<T> extends Classifier<T> {

    @Override
    protected FeatureClass<T> valueMapper(T value, Feature<T> feature) {
        return new FeatureClass<>(feature, value, null);
    }
}
