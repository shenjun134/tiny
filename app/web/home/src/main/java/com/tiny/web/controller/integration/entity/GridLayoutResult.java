package com.tiny.web.controller.integration.entity;

import com.tiny.web.controller.integration.enums.LayoutEnum;

import java.util.ArrayList;
import java.util.List;

public class GridLayoutResult extends LayoutResult {
    private static final long serialVersionUID = 399250768108512318L;

    public static GridLayoutResult newInstance(LayoutResult layoutResult) {
        GridLayoutResult result = new GridLayoutResult();
        result.copy(layoutResult);
        result.setType(LayoutEnum.GRID.getCode());
        return result;
    }

    private int beginX;

    private int beginY;

    private List<RectangleVO> allList = new ArrayList<>();

    public GridLayoutResult() {
        this.type = LayoutEnum.GRID.getCode();
    }

    public List<RectangleVO> getAllList() {
        return allList;
    }

    public void setAllList(List<RectangleVO> allList) {
        this.allList = allList;
    }

    public int getBeginX() {
        return beginX;
    }

    public void setBeginX(int beginX) {
        this.beginX = beginX;
    }

    public int getBeginY() {
        return beginY;
    }

    public void setBeginY(int beginY) {
        this.beginY = beginY;
    }
}
