package com.tiny.web.controller.http.request;

import com.tiny.common.base.ToString;

public class RecognitionReq extends ToString {

    private static final long serialVersionUID = 4349504382596963580L;

    /**
     * recognition image name
     */
    private String reconImage;

    /**
     * WEB-INFO path
     */
    private String imageWebPath;

    /**
     * absolute path
     */
    private String imageAbsPath;

    /**
     * a image id which come from learn machine
     */
    private String validateId;

    private String layoutType;

    private String layoutTag;

    private String layoutId;

    private String comments;

    public String getReconImage() {
        return reconImage;
    }

    public void setReconImage(String reconImage) {
        this.reconImage = reconImage;
    }

    public String getValidateId() {
        return validateId;
    }

    public void setValidateId(String validateId) {
        this.validateId = validateId;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public String getLayoutTag() {
        return layoutTag;
    }

    public void setLayoutTag(String layoutTag) {
        this.layoutTag = layoutTag;
    }

    public String getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(String layoutId) {
        this.layoutId = layoutId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImageWebPath() {
        return imageWebPath;
    }

    public void setImageWebPath(String imageWebPath) {
        this.imageWebPath = imageWebPath;
    }

    public String getImageAbsPath() {
        return imageAbsPath;
    }

    public void setImageAbsPath(String imageAbsPath) {
        this.imageAbsPath = imageAbsPath;
    }
}
