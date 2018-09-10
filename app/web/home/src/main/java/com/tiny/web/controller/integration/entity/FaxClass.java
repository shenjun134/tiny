package com.tiny.web.controller.integration.entity;

public class FaxClass {
    private String class_id;

    private double probability;

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "FaxClass{" +
                "class_id='" + class_id + '\'' +
                ", probability=" + probability +
                '}';
    }
}
