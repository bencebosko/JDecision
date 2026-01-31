package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* Classifies a record by a feature with the given mapping rule. If a Record cannot be obviously classified exception is thrown. */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class Classifier<T> {

    private final Map<T, FeatureClass<T>> classes = new HashMap<>();

    protected abstract FeatureClass<T> valueMapper(T value, Feature<T> feature);

    @SuppressWarnings("unchecked")
    FeatureClass<T> classify(Record record, Feature<T> feature) {
        final var value = (T) record.getValue(feature);
        final var featureClass = classes.computeIfAbsent(value, value_ -> valueMapper(value, feature));
        if (Objects.isNull(featureClass)) {
            throw new DecisionTreeException("Class not found for feature " + feature.getName() + " with value: " + value);
        }
        return featureClass;
    }
}
