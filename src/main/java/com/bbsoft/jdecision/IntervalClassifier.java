package com.bbsoft.jdecision;

import java.util.function.Function;

/* Classifies a value to an interval class. */
class IntervalClassifier<T> extends Classifier<T> {

    private final Function<T, Interval<T>> classifierFn;

    IntervalClassifier(Function<T, Interval<T>> classifierFn) {
        super();
        this.classifierFn = classifierFn;
    }

    @Override
    protected VariableClass<T> valueMapper(T value, Variable<T> variable) {
        return new VariableClass<>(variable, null, classifierFn.apply(value));
    }
}
