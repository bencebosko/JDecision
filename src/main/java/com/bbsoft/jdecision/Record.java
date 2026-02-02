package com.bbsoft.jdecision;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* A row of Variables. If a record has missing Variable exception is thrown. */
@RequiredArgsConstructor
@ToString
public class Record {

    private final Map<Variable<?>, Object> data;

    public Record() {
        data = new HashMap<>();
    }

    public void setValue(Variable<?> variable, Object value) {
        data.put(variable, value);
    }

    public Object getValue(Variable<?> variable) {
        var value = data.get(variable);
        if (Objects.isNull(value)) {
            throw new DecisionTreeException("Record has missing variable: " + variable.getName() + ". " + this);
        }
        return value;
    }
}
