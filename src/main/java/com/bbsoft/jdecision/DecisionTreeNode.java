package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract class DecisionTreeNode {

    protected final List<Record> records;
    @Getter(AccessLevel.PACKAGE)
    protected final List<Variable<Object>> remainingVariables;
    protected final TargetVariable<Object> targetVariable;
    private final boolean isRegression;
    /* Set after splitting the parent. Null for the root node. */
    private VariableClass<?> variableClass;
    /* Set after splitting the node. Null for leaf nodes. */
    @Getter(AccessLevel.PACKAGE)
    protected Variable<Object> splittingVariable;
    /* Set after splitting the node. Null for leaf nodes. */
    private List<DecisionTreeNode> children;

    protected DecisionTreeNode(List<Record> records,
                               List<Variable<Object>> remainingVariables,
                               TargetVariable<Object> targetVariable,
                               boolean isRegression,
                               VariableClass<?> variableClass) {
        this.records = Collections.unmodifiableList(records);
        this.remainingVariables = remainingVariables;
        this.targetVariable = targetVariable;
        this.isRegression = isRegression;
        this.variableClass = variableClass;
    }

    /* Returns null for Leaf nodes. */
    protected abstract Optional<Map<VariableClass<?>, List<Record>>> split();

    protected ClassificationDTO createClassification(Variable<Object> variable, List<Record> records) {
        final var totalCount = records.size();
        final Map<VariableClass<?>, List<Record>> classification = new HashMap<>();
        Double classificationMean = isRegression ? 0.0 : null;
        for (Record record : records) {
            final VariableClass<?> variableClass = variable.getClassifier().classify(record, variable);
            if (isRegression) {
                final var targetValue = getTargetValue(record, targetVariable);
                classificationMean = classificationMean + (targetValue / totalCount);
                variableClass.addTargetValue(targetValue, totalCount);
            } else {
                variableClass.addTargetClass(record, totalCount, targetVariable);
            }
            var recordsOfClass = classification.computeIfAbsent(variableClass, key -> new ArrayList<>());
            recordsOfClass.add(record);
        }
        return new ClassificationDTO(classification, classificationMean);
    }

    void setChildren(List<DecisionTreeNode> children) {
        this.children = Collections.unmodifiableList(children);
    }

    private double getTargetValue(Record record, TargetVariable<?> targetVariable) {
        var value = record.getValue(targetVariable);
        if (value instanceof Double) {
            return (double) value;
        }
        throw new DecisionTreeException("Record has invalid target variable: value must be double in regression mode. " + record);
    }
}
