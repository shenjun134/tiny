package com.tiny.web.controller.ocr;

import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.http.request.ReconSubReq;
import com.tiny.web.controller.integration.factory.ApiFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

@Controller
public class ApiController extends OCRController {
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(ApiController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private ApiFactory apiFactory;

    private String webInfAbsolutePath;

    @PostConstruct
    public void init() {
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").toString();
        logger.info("SignatureController classPath:" + classPath);
        webInfAbsolutePath = StringUtils.substring(classPath, 0, classPath.indexOf("/classes"));
        if (StringUtils.startsWith(webInfAbsolutePath, SignatureController.Constant.filePrefix)) {
            webInfAbsolutePath = webInfAbsolutePath.substring(SignatureController.Constant.filePrefix.length());
        }
        logger.info("ApiController init end... webInfAbsolutePath:" + webInfAbsolutePath);
    }


    @RequestMapping(path = UrlCenter.API.LAYOUT_RECON, method = RequestMethod.POST)
    @ResponseBody
    public Object layoutRecon(@RequestBody RecognitionReq recognitionReq) {
        LogUtil.info(logger, "begin to layoutRecon with - {0}", recognitionReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            setAbsPath(recognitionReq);
            return baseJsonResult.markeSuccess("Layout recognition successfully", apiFactory.getService().recognitionLayout(recognitionReq));
        } catch (Exception e) {
            logger.error("layoutRecon error - " + recognitionReq, e);
            baseJsonResult.marketFail(e.getLocalizedMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = UrlCenter.API.DETAIL_RECON, method = RequestMethod.POST)
    @ResponseBody
    public Object detailRecon(@RequestBody RecognitionReq recognitionReq) {
        LogUtil.info(logger, "begin to detailRecon with - {0}", recognitionReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            setAbsPath(recognitionReq);
            return baseJsonResult.markeSuccess("Detail recognition successfully", apiFactory.getService().recognitionContent(recognitionReq));
        } catch (Exception e) {
            logger.error("detailRecon error - " + recognitionReq, e);
            baseJsonResult.marketFail(e.getLocalizedMessage());
        }
        return baseJsonResult;
    }


    @RequestMapping(path = UrlCenter.API.SUBMIT_RECON, method = RequestMethod.POST)
    @ResponseBody
    public Object submitRecon(@RequestBody ReconSubReq reconSubReq) {
        LogUtil.info(logger, "begin to submitRecon with - {0}", reconSubReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            return baseJsonResult.markeSuccess("Submit recognition content successfully!", true);
        } catch (Exception e) {
            logger.error("submitRecon error - " + reconSubReq, e);
            baseJsonResult.marketFail(e.getLocalizedMessage());
        }
        return baseJsonResult;
    }

    /**
     * @param recognitionReq
     */
    private void setAbsPath(RecognitionReq recognitionReq) {
        String imgPath = recognitionReq.getImageWebPath();
        Assert.hasText(imgPath, "image web path is blank");
        String realPath = imgPath;
        String contextPath = context.getContextPath();
        if (StringUtils.startsWith(imgPath, contextPath)) {
            realPath = imgPath.substring(contextPath.length(), imgPath.length());
        }
        recognitionReq.setImageAbsPath(webInfAbsolutePath + realPath);
    }
}
