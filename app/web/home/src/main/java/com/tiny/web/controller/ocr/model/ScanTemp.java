package com.tiny.web.controller.ocr.model;

import com.tiny.common.base.ToString;

import java.util.ArrayList;
import java.util.List;

public class ScanTemp extends ToString {
    private static final long serialVersionUID = 5656817113505831859L;

    private String name;
    private String key;

    private List<Field> fieldList = new ArrayList<Field>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public void addField(Field field) {
        this.fieldList.add(field);
    }
}
