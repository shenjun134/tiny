package com.tiny.web.controller.http.request;

import com.tiny.common.base.ToString;
import com.tiny.web.controller.ocr.model.RectVO;

import java.util.ArrayList;
import java.util.List;

public class ReconSubReq extends ToString {
    private static final long serialVersionUID = 7192794875721791790L;

    private String imageName;

    private String imagePath;

    private String layoutType;

    private List<RectVO> detailList = new ArrayList<>();

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public List<RectVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<RectVO> detailList) {
        this.detailList = detailList;
    }
}
