package com.bbsoft.jdecision;

import java.util.function.Function;

public class FeatureFactory {

    public <T> Feature<T> createFeature(String name) {
        final Feature<T> feature = new Feature<>(name);
        feature.setClassifier(new ExactValueClassifier<>(feature));
        return feature;
    }

    public <T> Feature<T> createFeature(String name, Function<T, Interval<T>> intervalClassifierFn) {
        final Feature<T> feature = new Feature<>(name);
        feature.setClassifier(new IntervalClassifier<>(feature, intervalClassifierFn));
        return feature;
    }

    public <T> TargetFeature<T> createTargetFeature(String name) {
        final TargetFeature<T> targetFeature = new TargetFeature<>(name);
        targetFeature.setClassifier(new ExactValueClassifier<>(targetFeature));
        return targetFeature;
    }

    public <T> TargetFeature<T> createTargetFeature(String name, Function<T, Interval<T>> intervalClassifierFn) {
        final TargetFeature<T> targetFeature = new TargetFeature<>(name);
        targetFeature.setClassifier(new IntervalClassifier<>(targetFeature, intervalClassifierFn));
        return targetFeature;
    }
}
