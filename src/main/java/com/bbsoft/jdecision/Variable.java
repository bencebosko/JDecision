package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/* A column in the dataset. T is the type of the value (Integer, Double, String etc.) T must implement equals and hashCode */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Variable<T> {

    @Getter
    @EqualsAndHashCode.Include
    private final String name;
    @Getter(AccessLevel.PACKAGE)
    private final Classifier<T> classifier;
}
