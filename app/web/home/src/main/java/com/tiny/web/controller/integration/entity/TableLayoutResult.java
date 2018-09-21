package com.tiny.web.controller.integration.entity;

import com.tiny.web.controller.integration.enums.LayoutEnum;

import java.util.ArrayList;
import java.util.List;

public class TableLayoutResult extends LayoutResult {

    public static TableLayoutResult newInstance(LayoutResult layoutResult) {
        TableLayoutResult result = new TableLayoutResult();
        result.copy(layoutResult);
        result.setType(LayoutEnum.TABLE.getCode());
        return result;
    }

    private List<List<RectangleVO>> allList = new ArrayList<>();

    public TableLayoutResult() {
        this.type = LayoutEnum.TABLE.getCode();
    }

    public List<List<RectangleVO>> getAllList() {
        return allList;
    }

    public void setAllList(List<List<RectangleVO>> allList) {
        this.allList = allList;
    }


}
