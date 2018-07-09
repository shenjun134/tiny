package com.tiny.core.model;

import com.tiny.common.base.ToString;

import java.util.ArrayList;
import java.util.List;

public class FileInfo extends ToString {
    private static final long serialVersionUID = 5666372813711931474L;

    private String name;

    private Long length;

    private Long width;

    private Long height;

    private String type;

    private String path;

    private String shortName;

    private List<SubFile> subFileList = new ArrayList<SubFile>();

    public FileInfo(String name, Long length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SubFile> getSubFileList() {
        return subFileList;
    }

    public void setSubFileList(List<SubFile> subFileList) {
        this.subFileList = subFileList;
    }

    public void add(SubFile file) {
        subFileList.add(file);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
