package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DecisionTreeNodeFactory {

    private final DecisionMode decisionMode;

    DecisionTreeNode getRootNode(List<Record> records, List<Variable<Object>> variables, TargetVariable<Object> targetVariable) {
        switch (decisionMode) {
            case REGRESSION:
                return DecisionTreeNodeRegression.builder()
                    .records(records)
                    .remainingVariables(new ArrayList<>(variables))
                    .targetVariable(targetVariable)
                    .isRegression(true)
                    .build();
            case MANUAL_CLASSIFICATION:
                return DecisionTreeNodeManual.builder()
                    .records(records)
                    .remainingVariables(new ArrayList<>(variables))
                    .targetVariable(targetVariable)
                    .isRegression(false)
                    .build();
            default:
                return DecisionTreeNodeMinEntropy.builder()
                    .records(records)
                    .remainingVariables(new ArrayList<>(variables))
                    .targetVariable(targetVariable)
                    .isRegression(false)
                    .build();
        }
    }

    DecisionTreeNode getChildNode(List<Record> records,
                                  List<Variable<Object>> remainingVariables,
                                  TargetVariable<Object> targetVariable,
                                  VariableClass<?> variableClass) {
        switch (decisionMode) {
            case REGRESSION:
                return DecisionTreeNodeRegression.builder()
                    .records(records)
                    .remainingVariables(new ArrayList<>(remainingVariables))
                    .targetVariable(targetVariable)
                    .isRegression(true)
                    .variableClass(variableClass)
                    .build();
            case MANUAL_CLASSIFICATION:
                return DecisionTreeNodeManual.builder()
                    .records(records)
                    .remainingVariables(remainingVariables)
                    .targetVariable(targetVariable)
                    .isRegression(false)
                    .variableClass(variableClass)
                    .build();
            default:
                return DecisionTreeNodeMinEntropy.builder()
                    .records(records)
                    .remainingVariables(remainingVariables)
                    .targetVariable(targetVariable)
                    .isRegression(false)
                    .variableClass(variableClass)
                    .build();
        }
    }
}
