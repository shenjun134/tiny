package com.tiny.web.controller.ocr.model;

import com.tiny.common.base.ToString;

import java.util.ArrayList;
import java.util.List;

public class Box extends ToString {
    private String w;

    private String h;

    private String x;

    private String y;

    private String rate;

    private List<NameVO> nameList = new ArrayList<>();

    public Box() {
    }

    public Box(String w, String h, String x, String y) {
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
        this.rate = "0.0";
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getRate() {
        return rate;
    }

    public Box setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public List<NameVO> getNameList() {
        return nameList;
    }

    public Box setNameList(List<NameVO> nameList) {
        this.nameList = nameList;
        return this;
    }
}
