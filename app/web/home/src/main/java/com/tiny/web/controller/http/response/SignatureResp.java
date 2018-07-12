package com.tiny.web.controller.http.response;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.ocr.model.Box;

import java.util.ArrayList;
import java.util.List;

public class SignatureResp extends ToString {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4308540007069175138L;

    private String faxId;


    private List<Box> matchArea = new ArrayList<>();


    public List<Box> getMatchArea() {
        return matchArea;
    }

    public void setMatchArea(List<Box> matchArea) {
        this.matchArea = matchArea;
    }

    public String getFaxId() {
        return faxId;
    }

    public void setFaxId(String faxId) {
        this.faxId = faxId;
    }


}
