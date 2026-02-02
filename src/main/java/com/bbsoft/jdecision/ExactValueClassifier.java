package com.bbsoft.jdecision;

/* Classifies a value to a single class. */
class ExactValueClassifier<T> extends Classifier<T> {

    @Override
    protected VariableClass<T> valueMapper(T value, Variable<T> variable) {
        return new VariableClass<>(variable, value, null);
    }
}
