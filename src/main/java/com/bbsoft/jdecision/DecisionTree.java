package com.bbsoft.jdecision;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class DecisionTree {

    public static final int MAX_RECORDS = Integer.MAX_VALUE;
    public static final DecisionMode DEFAULT_DECISION_MODE = DecisionMode.REGRESSION;

    private final List<Variable<Object>> variables;
    private final TargetVariable<Object> targetVariable;
    /* Set when the tree is being build or rebuild. */
    private DecisionTreeNodeFactory nodeFactory;
    private DecisionTreeNode root;

    public DecisionTree(List<Variable<Object>> variables, TargetVariable<Object> targetVariable) {
        if (Objects.isNull(targetVariable)) {
            throw new DecisionTreeException("Target variable cannot be null.");
        }
        this.variables = Collections.unmodifiableList(variables);
        this.targetVariable = targetVariable;
    }

    /*
     Builds the DecisionTree iteratively from the root node, using the specified DecisionMode.
     Default DecisionMode is regression.

     Time complexity: O(N * M)
     where N is the length of the Records, M is the count of the Variables.

     Space complexity: O(N + M + C)
     where C is the sum classes of all Variable
     */
    public void buildTree(List<Record> records) {
        buildTree(records, DEFAULT_DECISION_MODE);
    }

    public void buildTree(List<Record> records, DecisionMode splitMode) {
        nodeFactory = new DecisionTreeNodeFactory(splitMode);
        root = null;
        doBuildTree(records);
    }

    public VariableClass<?> predict(Record record) {
        return null;
    }

    private void doBuildTree(List<Record> records) {
        final var root = nodeFactory.getRootNode(records, variables, targetVariable);
        final Queue<DecisionTreeNode> nodesToTraverse = new ArrayDeque<>();
        nodesToTraverse.add(root);
        while (!nodesToTraverse.isEmpty()) {
            final var currentNode = nodesToTraverse.remove();
            final var classification = currentNode.split();
            classification.ifPresent(classes -> {
                final var remainingChildFeatures = currentNode.getRemainingVariables();
                final List<DecisionTreeNode> children = new ArrayList<>();
                classes.forEach((featureClass, recordsOfClass) -> children.add(nodeFactory.getChildNode(recordsOfClass, remainingChildFeatures, targetVariable, featureClass)));
                currentNode.setChildren(children);
                nodesToTraverse.addAll(children);
            });
        }
        this.root = root;
    }
}
