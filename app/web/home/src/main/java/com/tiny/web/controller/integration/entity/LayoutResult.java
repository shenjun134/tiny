package com.tiny.web.controller.integration.entity;

import com.tiny.common.base.ToString;

public class LayoutResult extends ToString implements Cloneable {
    private static final long serialVersionUID = 4574838681403481119L;

    protected String type;

    protected String tag;

    protected String id;

    protected String comments;

    protected String probability;

    protected double width;

    protected double height;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void copy(LayoutResult result) {
        this.setType(result.getType());
        this.setTag(result.getTag());
        this.setId(result.getId());
        this.setComments(result.getComments());
        this.setProbability(result.getProbability());
    }
}