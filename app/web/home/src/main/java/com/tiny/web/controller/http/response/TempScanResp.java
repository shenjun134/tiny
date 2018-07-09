package com.tiny.web.controller.http.response;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.json.OcrTemp;

public class TempScanResp extends ToString {
    private static final long serialVersionUID = 8395331534564982366L;

    private OcrTemp temp;

    private Integer pageIndex;

    private Long parentWidth;

    private Long parentHeight;


    public OcrTemp getTemp() {
        return temp;
    }

    public void setTemp(OcrTemp temp) {
        this.temp = temp;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }


    public Long getParentWidth() {
        return parentWidth;
    }

    public void setParentWidth(Long parentWidth) {
        this.parentWidth = parentWidth;
    }

    public Long getParentHeight() {
        return parentHeight;
    }

    public void setParentHeight(Long parentHeight) {
        this.parentHeight = parentHeight;
    }
}
