package com.bbsoft.jdecision;

public class DecisionTreeException extends RuntimeException {

    private DecisionTreeException(String message) {
        super(message);
    }

    static DecisionTreeException nullTargetFeature() {
        return new DecisionTreeException("Target feature cannot be null.");
    }

    static DecisionTreeException missingFeature(Record record, Feature<?> feature) {
        return new DecisionTreeException("Record has missing feature: " + feature.getName() + " in " + record);
    }

    static DecisionTreeException classNotFoundForFeature(Feature<?> feature, Object value) {
        return new DecisionTreeException("Class not found for feature: " + feature.getName() + " with value " + value);
    }

    static DecisionTreeException invalidRegressionValue(Record record) {
        return new DecisionTreeException("Record has invalid regression value: target must be double in " + record);
    }
}
