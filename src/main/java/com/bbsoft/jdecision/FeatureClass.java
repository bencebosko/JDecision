package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/* Stores the values which belongs to a specific class of the Feature. */
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
    @Getter(AccessLevel.PACKAGE)
    private final Map<FeatureClass<?>, FeatureClass<?>> targetClasses = new HashMap<>();
    @Getter(AccessLevel.PACKAGE)
    private final RegressionAggregate regressionAggregate = new RegressionAggregate();
    @Setter(AccessLevel.PACKAGE)
    private int count = 0;
    @Setter(AccessLevel.PACKAGE)
    private double probability = 0.0;

    FeatureClass<?> computeTargetClass(FeatureClass<?> targetClass) {
        return targetClasses.computeIfAbsent(targetClass, cls -> cls);
    }
}
