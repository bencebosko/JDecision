package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/* Represents a class of a Variable's classification. Stores important aggregates of the TargetVariable. */
@RequiredArgsConstructor
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VariableClass<T> {

    @EqualsAndHashCode.Include
    private final Variable<T> variable;
    @EqualsAndHashCode.Include
    private final T exactValue;
    @EqualsAndHashCode.Include
    private final Interval<T> interval;
    private final RegressionAggregate regressionAggregate = new RegressionAggregate();
    private final Map<VariableClass<?>, VariableClass<?>> targetClassification = new HashMap<>();
    private int count = 0;
    private double probability = 0.0;

    void addTargetValue(double value, int totalCount) {
        incrementCount(totalCount);
        regressionAggregate.add(value);
    }

    void addTargetClass(Record record, int totalCount, TargetVariable<Object> targetFeature) {
        incrementCount(totalCount);
        var targetClass = targetClassification.computeIfAbsent(targetFeature.getClassifier().classify(record, targetFeature), cls -> cls);
        targetClass.incrementCount(count);
    }

    private void incrementCount(int totalCount) {
        count = count + 1;
        probability = (double) count / totalCount;
    }
}
