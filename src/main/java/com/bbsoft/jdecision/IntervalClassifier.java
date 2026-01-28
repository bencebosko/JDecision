package com.bbsoft.jdecision;

import java.util.function.Function;

class IntervalClassifier<T> extends Classifier<T> {

    private final Function<T, Interval<T>> classifierFn;

    IntervalClassifier(Feature<T> feature, Function<T, Interval<T>> classifierFn) {
        super(feature);
        this.classifierFn = classifierFn;
    }

    /* Maps a feature value to an interval class. */
    @Override
    protected FeatureClass<T> valueMapper(T value) {
        return new FeatureClass<>(feature, null, classifierFn.apply(value));
    }
}
