package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter(AccessLevel.PACKAGE)
class ClassificationDTO {

    private final Map<VariableClass<?>, List<Record>> classification;
    private final Double classificationMean;

    ClassificationDTO(Map<VariableClass<?>, List<Record>> classification, Double classificationMean) {
        this.classification = Collections.unmodifiableMap(classification);
        this.classificationMean = classificationMean;
    }
}
