package com.bbsoft.jdecision;

import java.util.function.Function;

public class VariableFactory {

    private final ExactValueClassifier<?> exactValueClassifier = new ExactValueClassifier<>();

    @SuppressWarnings("unchecked")
    public <T> Variable<T> createVariable(String name) {
        return new Variable<>(name, (Classifier<T>) exactValueClassifier);
    }

    @SuppressWarnings("unchecked")
    public <T> TargetVariable<T> createTargetVariable(String name) {
        return new TargetVariable<>(name, (Classifier<T>) exactValueClassifier);
    }

    public <T> Variable<T> createVariable(String name, Function<T, Interval<T>> intervalClassifierFn) {
        return new Variable<>(name, new IntervalClassifier<>(intervalClassifierFn));
    }

    public <T> TargetVariable<T> createTargetVariable(String name, Function<T, Interval<T>> intervalClassifierFn) {
        return new TargetVariable<>(name, new IntervalClassifier<>(intervalClassifierFn));
    }
}
