package com.tiny.web.controller.integration.entity;

import java.util.Arrays;

public class FaxResult {

    private SignatureResult[] faxes;

    private Integer page;

    public SignatureResult[] getFaxes() {
        return faxes;
    }

    public void setFaxes(SignatureResult[] faxes) {
        this.faxes = faxes;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "FaxResult{" +
                "faxes=" + Arrays.toString(faxes) +
                ", page=" + page +
                '}';
    }
}
