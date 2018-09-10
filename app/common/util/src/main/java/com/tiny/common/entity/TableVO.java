package com.tiny.common.entity;

import java.util.ArrayList;
import java.util.List;

public class TableVO {

    private RowVO header;

    private List<RowVO> bodyList = new ArrayList<>();

    public TableVO(RowVO header, List<RowVO> bodyList) {
        this.header = header;
        this.bodyList = bodyList;
    }

    public RowVO getHeader() {
        return header;
    }

    public void setHeader(RowVO header) {
        this.header = header;
    }

    public List<RowVO> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<RowVO> bodyList) {
        this.bodyList = bodyList;
    }

    public int rowSize() {
        return bodyList.size() + (header != null ? 1 : 0);
    }

    public int cloSize() {
        return (header != null ? header.getList().size() : 0);
    }

    @Override
    public String toString() {
        return "TableVO{" +
                "header=" + header +
                ", bodyList=" + bodyList +
                '}';
    }
}
