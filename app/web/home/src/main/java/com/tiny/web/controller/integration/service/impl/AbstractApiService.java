package com.tiny.web.controller.integration.service.impl;

import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.entity.ContentResult;
import com.tiny.web.controller.integration.entity.LayoutResult;
import com.tiny.web.controller.integration.service.ApiService;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

public abstract class AbstractApiService implements ApiService {

    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(AbstractApiService.class);

    @Override
    public List<LayoutResult> recognitionLayout(RecognitionReq request) {
        Long beginAt = System.currentTimeMillis();
        try {
            beforeLayout(request);
            List<LayoutResult> result = doLayoutRecon(request);
            afterLayout(request);
            return result;
        } catch (Exception e) {
            LogUtil.error(logger, e, "recognitionLayout error when request-{0}", request);
            throw new RuntimeException("Error:" + e.getLocalizedMessage(), e);
        } finally {
            Long used = System.currentTimeMillis() - beginAt;
            LogUtil.debug(logger, "recognitionLayout total used: {0}", used);
        }
    }

    @Override
    public ContentResult recognitionContent(RecognitionReq request) {
        Long beginAt = System.currentTimeMillis();
        try {
            beforeContent(request);
            ContentResult result = doContentRecon(request);
            afterContent(request);
            return result;
        } catch (Exception e) {
            LogUtil.error(logger, e, "recognitionContent error when request-{0}", request);
            throw new RuntimeException("Error:" + e.getLocalizedMessage(), e);
        } finally {
            Long used = System.currentTimeMillis() - beginAt;
            LogUtil.debug(logger, "recognitionContent total used: {0}", used);
        }
    }


    protected abstract List<LayoutResult> doLayoutRecon(RecognitionReq request);

    protected void beforeLayout(RecognitionReq request) {
        LogUtil.info(logger, "layout recognition before with - {0}", request);
    }

    protected void afterLayout(RecognitionReq request) {
        LogUtil.info(logger, "layout recognition after with - {0}", request);
    }

    protected abstract ContentResult doContentRecon(RecognitionReq request);

    protected void beforeContent(RecognitionReq request) {
        LogUtil.info(logger, "content recognition before with - {0}", request);
    }

    protected void afterContent(RecognitionReq request) {
        LogUtil.info(logger, "content recognition after with - {0}", request);
    }

    /**
     * @param request
     */
    protected void commonAssert(RecognitionReq request) {
        Assert.notNull(request, "request is null");
        Assert.hasText(request.getReconImage(), "image name is missing");
        Assert.hasText(request.getImageAbsPath(), "image abs path is missing");
        File file = new File(request.getImageAbsPath() + "/" + request.getReconImage());
        if (!file.exists()) {
            throw new IllegalArgumentException("Image is not existed.");
        }
        if (file.length() == 0) {
            throw new IllegalArgumentException("Image is blank.");
        }
    }

}
