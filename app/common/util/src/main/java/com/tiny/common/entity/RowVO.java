package com.tiny.common.entity;

import java.util.ArrayList;
import java.util.List;

public class RowVO {

    private List<CellVO> list = new ArrayList<>();

    public void add(CellVO cell) {
        this.list.add(cell);
    }

    public List<CellVO> getList() {
        return list;
    }

    public void setList(List<CellVO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RowVO{" +
                "list=" + list +
                '}';
    }
}
