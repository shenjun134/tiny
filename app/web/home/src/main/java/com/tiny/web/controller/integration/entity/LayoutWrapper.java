package com.tiny.web.controller.integration.entity;

import com.tiny.common.base.ToString;

import java.util.ArrayList;
import java.util.List;

public class LayoutWrapper extends ToString {
    private static final long serialVersionUID = -1437239141614336725L;

    private List<LayoutResult> layoutList = new ArrayList<>();

    protected String id;

    protected String comments;

    protected String probability;

    protected double width;

    protected double height;

    public List<LayoutResult> getLayoutList() {
        return layoutList;
    }

    public void setLayoutList(List<LayoutResult> layoutList) {
        this.layoutList = layoutList;
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
}
