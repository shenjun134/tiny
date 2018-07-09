package com.tiny.web.controller.ocr.model;

//import org.bytedeco.javacpp.opencv_core;

import java.awt.Point;

public abstract class Region {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Region() {
    }

    public void setLocation(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void reset() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

//    public opencv_core.Rect toRect()
//    {
//        return new opencv_core.Rect(x, y, width, height);
//    }

    @Override
    public String toString() {
        return String.format("Region r = %d, %d, %d, %d", getX(), getY(), getWidth(), getHeight());
    }
}
