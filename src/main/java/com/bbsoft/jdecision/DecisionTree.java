package com.bbsoft.jdecision;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class DecisionTree {

    public static final int MAX_RECORDS = Integer.MAX_VALUE;

    private final List<Feature<Object>> features;
    private final TargetFeature<Object> targetFeature;
    /* Set when the tree is being build or rebuild. */
    private DecisionTreeNodeFactory nodeFactory;
    private DecisionTreeNode root;

    public DecisionTree(List<Feature<Object>> features, TargetFeature<Object> targetFeature) {
        if (Objects.isNull(targetFeature)) {
            throw new DecisionTreeException("Target feature cannot be null.");
        }
        this.features = Collections.unmodifiableList(features);
        this.targetFeature = targetFeature;
    }

    /*
     Builds the DecisionTree iteratively from the root node, using the specified splitting mode.
     Default splitting is min entropy.

     Time complexity: O(N * M)
     where N is the length of the records, M is the count of the features.

     Space complexity: O(N + M + C)
     where C is the sum of Classes of all Features
     */
    public void buildTree(List<Record> records) {
        nodeFactory = new DecisionTreeNodeFactory(SplitMode.MIN_ENTROPY);
        doBuildTree(records);
    }

    public void buildTree(List<Record> records, SplitMode splitMode) {
        nodeFactory = new DecisionTreeNodeFactory(splitMode);
        doBuildTree(records);
    }

    public FeatureClass<?> predict(Record record) {
        return null;
    }

    private void doBuildTree(List<Record> records) {
        root = nodeFactory.getRootNode(records, features, targetFeature);
        final Queue<DecisionTreeNode> nodesToTraverse = new ArrayDeque<>();
        nodesToTraverse.add(root);
        while (!nodesToTraverse.isEmpty()) {
            final var currentNode = nodesToTraverse.remove();
            final Map<FeatureClass<?>, List<Record>> classification = currentNode.split();
            final var remainingChildFeatures = currentNode.getRemainingFeatures();
            final List<DecisionTreeNode> children = new ArrayList<>();
            classification.forEach((featureClass, recordsOfClass) -> children.add(nodeFactory.getChildNode(recordsOfClass, remainingChildFeatures, targetFeature, featureClass)));
            currentNode.setChildren(children);
            if (!currentNode.hasLeafChildren()) {
                nodesToTraverse.addAll(children);
            }
        }
    }
}
