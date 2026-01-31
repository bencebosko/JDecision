package com.bbsoft.jdecision;

import java.util.function.Function;

/* Classifies a value to an interval class. */
class IntervalClassifier<T> extends Classifier<T> {

    private final Function<T, Interval<T>> classifierFn;

    IntervalClassifier(Function<T, Interval<T>> classifierFn) {
        super();
        this.classifierFn = classifierFn;
    }

    @Override
    protected FeatureClass<T> valueMapper(T value, Feature<T> feature) {
        return new FeatureClass<>(feature, null, classifierFn.apply(value));
    }
}
