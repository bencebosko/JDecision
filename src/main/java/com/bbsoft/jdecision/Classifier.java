package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* Classifies a record by a Variable with the given mapping rule. If a Record cannot be classified exception is thrown. */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class Classifier<T> {

    private final Map<T, VariableClass<T>> classes = new HashMap<>();

    protected abstract VariableClass<T> valueMapper(T value, Variable<T> variable);

    @SuppressWarnings("unchecked")
    VariableClass<T> classify(Record record, Variable<T> variable) {
        final var value = (T) record.getValue(variable);
        final var variableClass = classes.computeIfAbsent(value, value_ -> valueMapper(value, variable));
        if (Objects.isNull(variableClass)) {
            throw new DecisionTreeException("Class not found for variable: " + variable.getName() + " with value " + value);
        }
        return variableClass;
    }
}
