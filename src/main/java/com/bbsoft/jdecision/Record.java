package com.bbsoft.jdecision;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* A row of feature-value pairs. If a record has missing feature exception is thrown. */
@RequiredArgsConstructor
public class Record {

    private final Map<Feature<?>, Object> data;

    public Record() {
        data = new HashMap<>();
    }

    public void setValue(Feature<?> feature, Object value) {
        data.put(feature, value);
    }

    public Object getValue(Feature<?> feature) {
        final var value = data.get(feature);
        if (Objects.isNull(value)) {
            throw new DecisionTreeException("Record has missing feature: " + feature.getName());
        }
        return value;
    }
}
