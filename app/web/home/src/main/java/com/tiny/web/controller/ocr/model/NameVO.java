package com.tiny.web.controller.ocr.model;

import com.tiny.common.base.ToString;
import org.apache.commons.lang.StringUtils;

public class NameVO extends ToString {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7568437531391270916L;

    private String first;

    private String second;

    private String full;

    private String email;

    private String rate;

    private String comments;

    private Long id;

    public NameVO() {
    }

    public NameVO(Long id, String src) {
        this.id = id;
        if (StringUtils.isBlank(src)) {
            return;
        }
        String[] arr = src.trim().split("\\|");
        if (arr == null || arr.length == 0) {
            return;
        }
        if (arr.length > 0) {
            this.full = arr[0];
        }
        if (arr.length > 1) {
            this.email = arr[1];
        }
        if (arr.length > 2) {
            this.first = arr[2];
        }
        if (arr.length > 3) {
            this.second = arr[3];
        }
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
