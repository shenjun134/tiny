package com.tiny.web.controller.integration.entity;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.integration.enums.TagEnum;

import java.util.ArrayList;
import java.util.List;

public class RectangleVO extends ToString {
    private static final long serialVersionUID = 5773245162801859566L;

    private String name;

    private String id;

    /**
     * text type: double, double, percentage, string, date,
     */
    private String type;

    private String text;

    private TagEnum tag;

    private String score;

    private double xmin;

    private double ymin;

    private double xmax;

    private double ymax;

    private double width;

    private double height;

    private List<CharVO> charList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TagEnum getTag() {
        return tag;
    }

    public double getXmin() {
        return xmin;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getYmin() {
        return ymin;
    }

    public void setYmin(double ymin) {
        this.ymin = ymin;
    }

    public double getXmax() {
        return xmax;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public double getYmax() {
        return ymax;
    }

    public void setYmax(double ymax) {
        this.ymax = ymax;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<CharVO> getCharList() {
        return charList;
    }

    public void setCharList(List<CharVO> charList) {
        this.charList = charList;
    }
}
