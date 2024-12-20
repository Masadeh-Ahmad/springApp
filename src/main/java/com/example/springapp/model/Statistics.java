package com.example.springapp.model;

public class Statistics {

    private float avg;
    private float max;
    private float min;
    private float median;

    public Statistics(float avg, float max, float min, float median) {

        this.avg = avg;
        this.max = max;
        this.min = min;
        this.median = median;
    }


    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(float median) {
        this.median = median;
    }
}
