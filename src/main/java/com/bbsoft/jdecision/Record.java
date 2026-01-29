package com.bbsoft.jdecision;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* A row of feature-value pairs. Feature represents a splitting column in the dataset. */
public class Record {

    private final Map<Feature<?>, Object> data = new HashMap<>();

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
