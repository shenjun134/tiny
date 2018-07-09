package com.tiny.web.controller.integration.service.impl;

import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.util.RandomUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("mockService")
public class MockSignature extends AbstractSignatureService {
    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(MockSignature.class);

    @Override
    protected void before(String imagePath, String imageName) {
        logger.info("mock before check ...");
        Assert.hasText(imagePath, "imagePath is blank");
        Assert.hasText(imageName, "imageName is blank");
    }

    @Override
    protected SignatureResp process(String imagePath, String imageName) {
        logger.info("mock process ...");
        return RandomUtil.matchSignature(imagePath, null);
    }

    @Override
    protected void after(String imagePath, String imageName) {
        logger.info("mock after done.");
    }



}
