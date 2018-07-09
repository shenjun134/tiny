package com.tiny.web.controller.http.request;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.ocr.model.Box;

public class SignatureReq extends ToString {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1453483402527957639L;

    private String pageIndex;

    private String name;

    private String email;

    private String comments;

    /**
     * if signature database do not find out the matched item,
     * but user want to match it, it will be the fixed signature pic
     */
    private String fixedImage;

    /**
     * below W,H,X,Y will be the fixed rectangle area in the signature picture
     */
    private Box fixedArea;

    /**
     *
     */
    private Box confirmArea;

    public SignatureReq() {

    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFixedImage() {
        return fixedImage;
    }

    public void setFixedImage(String fixedImage) {
        this.fixedImage = fixedImage;
    }

    public Box getFixedArea() {
        return fixedArea;
    }

    public void setFixedArea(Box fixedArea) {
        this.fixedArea = fixedArea;
    }

    public Box getConfirmArea() {
        return confirmArea;
    }

    public void setConfirmArea(Box confirmArea) {
        this.confirmArea = confirmArea;
    }
}
