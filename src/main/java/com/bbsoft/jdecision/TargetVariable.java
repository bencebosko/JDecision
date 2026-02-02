package com.bbsoft.jdecision;

public class TargetVariable<T> extends Variable<T> {

    TargetVariable(String name, Classifier<T> classifier) {
        super(name, classifier);
    }
}
