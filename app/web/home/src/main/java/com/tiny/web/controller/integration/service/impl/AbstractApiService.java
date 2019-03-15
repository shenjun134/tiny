package com.tiny.web.controller.integration.service.impl;

import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.entity.ContentResult;
import com.tiny.web.controller.integration.entity.LayoutResult;
import com.tiny.web.controller.integration.entity.LayoutWrapper;
import com.tiny.web.controller.integration.service.ApiService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class AbstractApiService extends AbstractIntegrationService implements ApiService {

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
    public LayoutWrapper recognitionLayout2(RecognitionReq request) {
        Long beginAt = System.currentTimeMillis();
        try {
            beforeLayout2(request);
            LayoutWrapper result = doLayoutRecon2(request);
            afterLayout2(request);
            return result;
        } catch (Exception e) {
            LogUtil.error(logger, e, "recognitionLayout2 error when request-{0}", request);
            throw new RuntimeException("Error:" + e.getLocalizedMessage(), e);
        } finally {
            Long used = System.currentTimeMillis() - beginAt;
            LogUtil.debug(logger, "recognitionLayout2 total used: {0}", used);
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

    @Override
    public List<ContentResult> recognitionContent2(RecognitionReq request) {
        Long beginAt = System.currentTimeMillis();
        try {
            beforeContent2(request);
            List<ContentResult> result = doContentRecon2(request);
            afterContent2(request);
            return result;
        } catch (Exception e) {
            LogUtil.error(logger, e, "recognitionContent2 error when request-{0}", request);
            throw new RuntimeException("Error:" + e.getLocalizedMessage(), e);
        } finally {
            Long used = System.currentTimeMillis() - beginAt;
            LogUtil.debug(logger, "recognitionContent2 total used: {0}", used);
        }
    }


    protected abstract List<LayoutResult> doLayoutRecon(RecognitionReq request);

    protected abstract LayoutWrapper doLayoutRecon2(RecognitionReq request);

    protected void beforeLayout(RecognitionReq request) {
        LogUtil.info(logger, "layout recognition before with - {0}", request);
    }

    protected void afterLayout(RecognitionReq request) {
        LogUtil.info(logger, "layout recognition after with - {0}", request);
    }

    protected void beforeLayout2(RecognitionReq request) {
        LogUtil.info(logger, "layout2 recognition before with - {0}", request);
    }

    protected void afterLayout2(RecognitionReq request) {
        LogUtil.info(logger, "layout2 recognition after with - {0}", request);
    }

    protected abstract ContentResult doContentRecon(RecognitionReq request);

    protected abstract List<ContentResult> doContentRecon2(RecognitionReq request) throws UnsupportedEncodingException;

    protected void beforeContent(RecognitionReq request) {
        LogUtil.info(logger, "content recognition before with - {0}", request);
    }

    protected void afterContent(RecognitionReq request) {
        LogUtil.info(logger, "content recognition after with - {0}", request);
    }

    protected void beforeContent2(RecognitionReq request) {
        LogUtil.info(logger, "content2 recognition before with - {0}", request);
        Assert.notNull(request);
        Assert.hasText(request.getLayoutId(), "layout id miss");
        Assert.hasText(request.getImageWebPath(), "image web path is miss");
        Assert.hasText(request.getReconImage(), "image name is miss");
        Assert.state(CollectionUtils.isNotEmpty(request.getSelectedLayout()), "Layout is empty");
    }

    protected void afterContent2(RecognitionReq request) {
        LogUtil.info(logger, "content2 recognition after with - {0}", request);
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
