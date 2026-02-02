package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class DecisionTreeNodeManual extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeManual(List<Record> records,
                           List<Variable<Object>> remainingVariables,
                           TargetVariable<Object> targetVariable,
                           boolean isRegression,
                           VariableClass<?> variableClass) {
        super(records, remainingVariables, targetVariable, isRegression, variableClass);
    }

    /* Splits the node with the next Variable in manual order. */
    @Override
    protected Optional<Map<VariableClass<?>, List<Record>>> split() {
        if (remainingVariables.isEmpty()) {
            return Optional.empty();
        } else {
            splittingVariable = remainingVariables.remove(0);
        }
        return Optional.of(createClassification(splittingVariable, records).getClassification());
    }
}
