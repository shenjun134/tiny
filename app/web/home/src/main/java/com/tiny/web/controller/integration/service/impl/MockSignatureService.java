package com.tiny.web.controller.integration.service.impl;

import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.entity.FixedFax;
import com.tiny.web.controller.integration.util.RandomUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("mockSignatureService")
public class MockSignatureService extends AbstractSignatureService {
    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(MockSignatureService.class);

    @Override
    protected void scanBefore(String imagePath, String imageName) {
        logger.info("mock scanBefore check ...");
        Assert.hasText(imagePath, "imagePath is blank");
        Assert.hasText(imageName, "imageName is blank");
    }

    @Override
    protected SignatureResp processScan(String imagePath, String imageName) {
        logger.info("mock processScan ...");
        return RandomUtil.matchSignature(imagePath, null);
    }

    @Override
    protected void scanAfter(String imagePath, String imageName) {
        logger.info("mock scanAfter done.");
    }

    @Override
    protected void fixBefore(FixedFax fixedFax) {
        Assert.notNull(fixedFax, "null fixedFax");

        Assert.hasText(fixedFax.getFixedImageId(), "fix image id is blank");

        Assert.notNull(fixedFax.getWriterId(), "writer id is miss");

        Assert.hasText(fixedFax.getWriterName(), "writer name is miss");

        Assert.notNull(fixedFax.getHeight(), "height is miss");

        Assert.notNull(fixedFax.getWidth(), "width is miss");

        Assert.notNull(fixedFax.getXmin(), "Xmin is miss");

        Assert.notNull(fixedFax.getYmin(), "Ymin is miss");
    }

    @Override
    protected void fixAfter(FixedFax fixedFax) {
        logger.info("mock fix after done");
    }

    @Override
    protected void processFix(FixedFax fixedFax, BaseJsonResult baseJsonResult) {
        logger.info("mock fix process done");
        baseJsonResult.markeSuccess("mock fix successfully", true);
    }


}
