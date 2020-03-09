package com.sayantan.coronatracker.model;

public class NumberStats {
    private String title;
    private double number;

    @Override
    public String toString() {
        return "NumberStats{" +
                "title=" + title +
                ", number=" + number +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
