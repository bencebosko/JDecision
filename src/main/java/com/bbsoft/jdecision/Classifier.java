package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class Classifier<T> {

    protected final Feature<T> feature;
    private final Map<T, FeatureClass<T>> classes = new HashMap<>();

    protected abstract FeatureClass<T> valueMapper(T value);

    /* Classifies a single value of the feature. */
    FeatureClass<T> classify(T value) {
        return classes.computeIfAbsent(value, value_ -> valueMapper(value));
    }
}
