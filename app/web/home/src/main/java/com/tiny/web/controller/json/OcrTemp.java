package com.tiny.web.controller.json;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.ocr.model.Field;

import java.util.ArrayList;
import java.util.List;

public class OcrTemp extends ToString {
    private static final long serialVersionUID = -1217057045988763910L;

    private String tempKey;

    private List<Field> fieldList = new ArrayList<Field>();

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * @param field
     */
    public void addField(Field field) {
        this.fieldList.add(field);
    }
}
