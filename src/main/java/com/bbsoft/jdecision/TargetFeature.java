package com.bbsoft.jdecision;

public class TargetFeature<T> extends Feature<T> {

    TargetFeature(String name, Classifier<T> classifier) {
        super(name, classifier);
    }
}
