package com.bbsoft.jdecision;

import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

class DecisionTreeNodeMinEntropy extends DecisionTreeNode {

    @Builder
    DecisionTreeNodeMinEntropy(List<Record> records,
                               List<Variable<Object>> remainingVariables,
                               TargetVariable<Object> targetVariable,
                               boolean isRegression,
                               VariableClass<?> variableClass) {
        super(records, remainingVariables, targetVariable, isRegression, variableClass);
    }

    /* Finds min entropy split at specific node of the tree. */
    @Override
    protected Optional<Map<VariableClass<?>, List<Record>>> split() {
        if (remainingVariables.isEmpty()) {
            return Optional.empty();
        } else {
            Map<VariableClass<?>, List<Record>> optimalClassification = Collections.emptyMap();
            var minEntropy = Double.MAX_VALUE;
            for (Variable<Object> variable : remainingVariables) {
                final var classificationDTO = createClassification(variable, records);
                final var entropy = getAverageEntropy(classificationDTO.getClassification());
                if (entropy < minEntropy) {
                    splittingVariable = variable;
                    optimalClassification = classificationDTO.getClassification();
                    minEntropy = entropy;
                }
            }
            if (Objects.nonNull(splittingVariable)) {
                remainingVariables.remove(splittingVariable);
            }
            return Optional.of(optimalClassification);
        }
    }

    private double getAverageEntropy(Map<VariableClass<?>, List<Record>> classification) {
        final var classCount = classification.size();
        var entropy = 0.0;
        for (VariableClass<?> variableClass : classification.keySet()) {
            var entropyOfClass = 0.0;
            for (VariableClass<?> targetClass : variableClass.getTargetClassification().keySet()) {
                var p = targetClass.getProbability();
                entropyOfClass += p * (Math.log(p) / Math.log(2));
            }
            entropy += entropyOfClass;
        }
        return (-1 * entropy) / classCount;
    }
}
