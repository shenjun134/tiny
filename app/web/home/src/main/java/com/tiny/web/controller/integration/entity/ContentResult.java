package com.tiny.web.controller.integration.entity;

import com.tiny.common.base.ToString;

public class ContentResult extends ToString {
    private static final long serialVersionUID = -1540493060018787774L;

    private LayoutResult result;

    public LayoutResult getResult() {
        return result;
    }

    public void setResult(LayoutResult result) {
        this.result = result;
    }
}
