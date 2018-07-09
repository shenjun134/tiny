package com.tiny.web.controller.integration.entity;

import java.util.Arrays;

public class MatchResult {

    private Author[] writers;

    private Double height;

    private Double width;

    private Long score;

    private Double xmin;

    private Double ymin;

    public Author[] getWriters() {
        return writers;
    }

    public void setWriters(Author[] writers) {
        this.writers = writers;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Double getXmin() {
        return xmin;
    }

    public void setXmin(Double xmin) {
        this.xmin = xmin;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
        this.ymin = ymin;
    }

    @Override
    public String toString() {
        return "MatchResult{" +
                "writers=" + Arrays.toString(writers) +
                ", height=" + height +
                ", width=" + width +
                ", score=" + score +
                ", xmin=" + xmin +
                ", ymin=" + ymin +
                '}';
    }
}
