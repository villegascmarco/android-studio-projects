package com.example.vcimpregression;

public class Regression {
    private double xValue;
    private double yValue;
    private double xSquared;
    private double xTimesY;
    private double ySquared;

    public Regression(String xValue, String yValue) {
        this.xValue = Double.parseDouble(xValue);
        this.yValue = Double.parseDouble(yValue);

        calculateExtraData();
    }

    public double getxValue() {
        return xValue;
    }

    public double getyValue() {
        return yValue;
    }

    public double getxSquared() {
        return xSquared;
    }

    public double getxTimesY() {
        return xTimesY;
    }

    public double getySquared() {
        return ySquared;
    }

    private void calculateExtraData() {
        xSquared = (double) Math.pow(xValue, 2);//(int) to delete decimals
        xTimesY = xValue * yValue;
        ySquared = (double) Math.pow(yValue, 2);//(int) to delete decimals
    }
}
