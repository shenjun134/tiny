package com.tiny.core.model;

import com.tiny.common.base.ToString;

public class MarkBox extends ToString {

    private static final long serialVersionUID = -6646403569260629070L;

    private String name;

    private Long startX;

    private Long startY;

    private Long width;

    private Long height;

    public MarkBox(String name, Long startX, Long startY, Long width, Long height) {
        this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getStartX() {
        return startX;
    }

    public void setStartX(Long startX) {
        this.startX = startX;
    }

    public Long getStartY() {
        return startY;
    }

    public void setStartY(Long startY) {
        this.startY = startY;
    }
}
