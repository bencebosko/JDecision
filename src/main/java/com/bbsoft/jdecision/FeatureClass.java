package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/* Represents a class of a feature's classification. Stores data of the classified records. */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FeatureClass<T> {

    @EqualsAndHashCode.Include
    private final Feature<T> feature;
    @EqualsAndHashCode.Include
    private final T exactValue;
    @EqualsAndHashCode.Include
    private final Interval<T> interval;
    private final RegressionAggregate regressionAggregate = new RegressionAggregate();
    @Getter(AccessLevel.PACKAGE)
    private final Map<FeatureClass<?>, FeatureClass<?>> targetClassification = new HashMap<>();
    private int count = 0;
    private double probability = 0.0;

    void addTargetValue(double value, int totalCount) {
        incrementCount(totalCount);
        regressionAggregate.add(value);
    }

    void addTargetClass(Record record, int totalCount, TargetFeature<Object> targetFeature) {
        incrementCount(totalCount);
        var targetClass = targetClassification.computeIfAbsent(targetFeature.getClassifier().classify(record, targetFeature), cls -> cls);
        targetClass.incrementCount(count);
    }

    private void incrementCount(int totalCount) {
        count = count + 1;
        probability = (double) count / totalCount;
    }
}
