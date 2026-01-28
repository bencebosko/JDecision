package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    @Setter(AccessLevel.PACKAGE)
    private double probability = 0.0;
}
