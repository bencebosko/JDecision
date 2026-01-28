package com.bbsoft.jdecision;

import org.junit.jupiter.api.BeforeEach;

public class DecisionTreeTest {

    private final FeatureFactory featureFactory = new FeatureFactory();
    private DecisionTree tree;


    @BeforeEach
    public void buildTree() {
        final Feature<Integer> age = featureFactory.createFeature("age", (value) -> Interval.of(0, 80));

        Record record1 = new Record();
        record1.setValue(age, "data.getAge()");

    }
}
