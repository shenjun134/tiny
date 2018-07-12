package com.tiny.web.controller.integration.entity;

public class FixedFax {
    private String fixedImageId;

    private Double height;

    private Double width;

    private Double xmin;

    private Double ymin;

    private Long writerId;

    private String comments;

    private String writerName;

    private String writerEmail;

    public String getFixedImageId() {
        return fixedImageId;
    }

    public void setFixedImageId(String fixedImageId) {
        this.fixedImageId = fixedImageId;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getXmin() {
        return xmin;
    }

    public void setXmin(Double xmin) {
        this.xmin = xmin;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
        this.ymin = ymin;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    @Override
    public String toString() {
        return "FixedFax{" +
                "fixedImageId='" + fixedImageId + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", xmin=" + xmin +
                ", ymin=" + ymin +
                ", writerId=" + writerId +
                ", comments='" + comments + '\'' +
                ", writerName='" + writerName + '\'' +
                ", writerEmail='" + writerEmail + '\'' +
                '}';
    }
}
