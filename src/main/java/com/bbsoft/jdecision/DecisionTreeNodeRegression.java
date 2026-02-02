package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

class DecisionTreeNodeRegression extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeRegression(List<Record> records,
                               List<Variable<Object>> remainingVariables,
                               TargetVariable<Object> targetVariable,
                               boolean isRegression,
                               VariableClass<?> variableClass) {
        super(records, remainingVariables, targetVariable, isRegression, variableClass);
    }

    /* Finds min squared error split at specific node of the tree. */
    @Override
    protected Optional<Map<VariableClass<?>, List<Record>>> split() {
        if (remainingVariables.isEmpty()) {
            return Optional.empty();
        } else {
            Map<VariableClass<?>, List<Record>> optimalClassification = Collections.emptyMap();
            var minSquaredError = Double.MAX_VALUE;
            for (Variable<Object> variable : remainingVariables) {
                final var classificationDTO = createClassification(variable, records);
                final var squaredError = getSquaredError(classificationDTO);
                if (squaredError < minSquaredError) {
                    splittingVariable = variable;
                    optimalClassification = classificationDTO.getClassification();
                    minSquaredError = squaredError;
                }
            }
            if (Objects.nonNull(splittingVariable)) {
                remainingVariables.remove(splittingVariable);
            }
            return Optional.of(optimalClassification);
        }
    }

    private double getSquaredError(ClassificationDTO classification) {
        final var classificationMean = classification.getClassificationMean();
        var squaredError = 0.0;
        for (VariableClass<?> cls : classification.getClassification().keySet()) {
            var error = Math.abs(classificationMean - cls.getRegressionAggregate().getMeanValue());
            squaredError += error * error;
        }
        return squaredError;
    }
}
