package com.bbsoft.jdecision;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RegressionAggregate {

    @Setter(AccessLevel.PACKAGE)
    private Double meanValue = null;
    @Setter(AccessLevel.PACKAGE)
    private Double meanSquaredError = null;
}
