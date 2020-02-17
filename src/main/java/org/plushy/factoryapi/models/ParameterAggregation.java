package org.plushy.factoryapi.models;

public class ParameterAggregation {
    private Parameter parameter;
    private double min;
    private double max;
    private double median;
    private double average;

    public ParameterAggregation(final Parameter parameter, final double min, final double max, final double median,
            final double average) {
        this.parameter = parameter;
        this.min = min;
        this.max = max;
        this.median = median;
        this.average = average;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(final Parameter parameter) {
        this.parameter = parameter;
    }

    public double getMin() {
        return min;
    }

    public void setMin(final double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(final double max) {
        this.max = max;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(final double median) {
        this.median = median;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(final double average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "ParameterAggregation [average=" + average + ", max=" + max + ", median=" + median + ", min=" + min
                + ", parameter=" + parameter + "]";
    }
}
