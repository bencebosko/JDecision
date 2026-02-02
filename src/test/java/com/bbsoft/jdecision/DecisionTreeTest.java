package com.bbsoft.jdecision;

import org.junit.jupiter.api.BeforeEach;

public class DecisionTreeTest {

    private final VariableFactory variableFactory = new VariableFactory();
    private DecisionTree tree;


    @BeforeEach
    public void buildTree() {
        final Variable<Integer> age = variableFactory.createVariable("age", (value) -> Interval.of(0, 80));

        Record record1 = new Record();
        record1.setValue(age, "data.getAge()");

    }
}
