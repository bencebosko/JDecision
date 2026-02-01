package com.bbsoft.jdecision;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/* Stores mean value when aggregating the target variable in regression mode. Uses BigDecimal to avoid double overflow. */
public class RegressionAggregate {

    private double valueSum;
    private BigDecimal valueSumAccumulator;
    private int count;
    private Double meanValue;

    RegressionAggregate() {
        valueSum = 0.0;
        count = 0;
        valueSumAccumulator = null;
        meanValue = null;
    }

    void add(double value) {
        if (isAccumulatorMode()) {
            var valueToAdd = new BigDecimal(value);
            valueSumAccumulator = valueSumAccumulator.add(valueToAdd);
        } else {
            if (isValueOverflow(value)) {
                var currentSum = new BigDecimal(valueSum);
                var valueToAdd = new BigDecimal(value);
                valueSumAccumulator = currentSum.add(valueToAdd);
            } else {
                valueSum = valueSum + value;
            }
        }
        meanValue = null;
        count = count + 1;
    }

    public Double getMeanValue() {
        if (count == 0) {
            return null;
        }
        if (Objects.isNull(meanValue)) {
            if (isAccumulatorMode()) {
                var totalCount = new BigDecimal(count);
                meanValue = valueSumAccumulator.divide(totalCount, RoundingMode.DOWN).doubleValue();
            } else {
                meanValue = valueSum / count;
            }
        }
        return meanValue;
    }

    private boolean isValueOverflow(double value) {
        return Double.MAX_VALUE - value > valueSum;
    }

    private boolean isAccumulatorMode() {
        return Objects.nonNull(valueSumAccumulator);
    }
}
