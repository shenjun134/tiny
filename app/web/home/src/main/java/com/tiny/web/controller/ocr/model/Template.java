package com.tiny.web.controller.ocr.model;

import org.apache.commons.lang.StringUtils;

import com.tiny.web.controller.ocr.util.PropUtil;

import static com.tiny.web.controller.ocr.util.Constants.OCR_TEMPLATE;

public class Template extends Region {
    public Template() {
    }

    private String name;
    private String path;

    private int floatX;
    private int floatY;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getFloatX() {
        return floatX;
    }

    public int getFloatY() {
        return floatY;
    }

    public void setFloatX(int floatX) {
        this.floatX = floatX;
    }

    public void setFloatY(int floatY) {
        this.floatY = floatY;
    }

    public void setFloat(int floatX, int floatY) {
        this.floatX = floatX;
        this.floatY = floatY;
    }

    public Template loadProperty() {
        String templateProp = PropUtil.load(OCR_TEMPLATE).get(name);
        String[] arr = StringUtils.remove(templateProp, ' ').split(",");
        this.setLocation(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]) - Integer.parseInt(arr[0]), Integer.parseInt(arr[3]) - Integer.parseInt(arr[1]));
        this.setFloat(Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
        return this;
    }
}
