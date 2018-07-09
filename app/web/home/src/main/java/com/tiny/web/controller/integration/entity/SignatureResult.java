package com.tiny.web.controller.integration.entity;

import java.util.Arrays;

public class SignatureResult {
    private MatchResult[] signatures;

    private String fax_id;

    private Double width;

    private Double height;

    public MatchResult[] getSignatures() {
        return signatures;
    }

    public void setSignatures(MatchResult[] signatures) {
        this.signatures = signatures;
    }

    public String getFax_id() {
        return fax_id;
    }

    public void setFax_id(String fax_id) {
        this.fax_id = fax_id;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "SignatureResult{" +
                "signatures=" + Arrays.toString(signatures) +
                ", fax_id='" + fax_id + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
