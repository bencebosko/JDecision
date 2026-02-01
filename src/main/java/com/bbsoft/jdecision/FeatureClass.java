package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/* Represents a class of a feature classification. Stores data of the classified records. */
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

    void addRecord(Record record, int recordCount, TargetFeature<Object> targetFeature, boolean isRegression) {
        count = count + 1;
        setProbability(recordCount);
        if (isRegression) {
            regressionAggregate.add(getValueForRegression(record, targetFeature));
        } else {
            final var targetClass = targetClassification.computeIfAbsent(targetFeature.getClassifier().classify(record, targetFeature), cls -> cls);
            targetClass.count = targetClass.count + 1;
            targetClass.setProbability(recordCount);
        }
    }

    private void setProbability(int totalCount) {
        probability = (double) count / totalCount;
    }

    private double getValueForRegression(Record record, TargetFeature<?> targetFeature) {
        var value = record.getValue(targetFeature);
        if (value instanceof Double) {
            return (double) value;
        }
        throw DecisionTreeException.invalidRegressionValue(record);
    }
}
