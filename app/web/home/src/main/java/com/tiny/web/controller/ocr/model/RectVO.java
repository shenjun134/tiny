package com.tiny.web.controller.ocr.model;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.integration.enums.TagEnum;

public class RectVO extends ToString {

    private static final long serialVersionUID = 3735796301000793966L;
    private String name;

    private String id;

    /**
     * text type: int, float, percentage, string, date,
     */
    private String type;

    private String fixText;

    private String srcText;

    private TagEnum tag;

    private String xmin;

    private String ymin;

    private String xmax;

    private String ymax;

    private String width;

    private String height;

    private String rowIndex;

    private String colIndex;

    private String bizTag;

    private String comments;

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

    public String getFixText() {
        return fixText;
    }

    public void setFixText(String fixText) {
        this.fixText = fixText;
    }

    public String getSrcText() {
        return srcText;
    }

    public void setSrcText(String srcText) {
        this.srcText = srcText;
    }

    public TagEnum getTag() {
        return tag;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getYmin() {
        return ymin;
    }

    public void setYmin(String ymin) {
        this.ymin = ymin;
    }

    public String getXmax() {
        return xmax;
    }

    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    public String getYmax() {
        return ymax;
    }

    public void setYmax(String ymax) {
        this.ymax = ymax;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(String rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getColIndex() {
        return colIndex;
    }

    public void setColIndex(String colIndex) {
        this.colIndex = colIndex;
    }

    public String getBizTag() {
        return bizTag;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
