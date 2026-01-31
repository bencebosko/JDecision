package com.bbsoft.jdecision;

import java.util.function.Function;

public class FeatureFactory {

    private final ExactValueClassifier<?> exactValueClassifier = new ExactValueClassifier<>();

    @SuppressWarnings("unchecked")
    public <T> Feature<T> createFeature(String name) {
        return new Feature<>(name, (Classifier<T>) exactValueClassifier);
    }

    @SuppressWarnings("unchecked")
    public <T> TargetFeature<T> createTargetFeature(String name) {
        return new TargetFeature<>(name, (Classifier<T>) exactValueClassifier);
    }

    public <T> Feature<T> createFeature(String name, Function<T, Interval<T>> intervalClassifierFn) {
        return new Feature<>(name, new IntervalClassifier<>(intervalClassifierFn));
    }

    public <T> TargetFeature<T> createTargetFeature(String name, Function<T, Interval<T>> intervalClassifierFn) {
        return new TargetFeature<>(name, new IntervalClassifier<>(intervalClassifierFn));
    }
}
