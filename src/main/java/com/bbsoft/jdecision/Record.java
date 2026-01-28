package com.bbsoft.jdecision;

import java.util.HashMap;
import java.util.Map;

/* A row of feature-value pairs. Feature represents a splitting column in the dataset. */
public class Record {

    private final Map<Feature<?>, Object> data = new HashMap<>();

    public void setValue(Feature<?> feature, Object value) {
        data.put(feature, value);
    }

    public Object getValue(Feature<?> feature) {
        return data.get(feature);
    }
}
