package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Interval<T> {

    @EqualsAndHashCode.Include
    private final T leftBound;
    @EqualsAndHashCode.Include
    private final T rightBound;

    public static <T> Interval<T> of(T leftBound, T rightBound) {
        return new Interval<>(leftBound, rightBound);
    }
}
