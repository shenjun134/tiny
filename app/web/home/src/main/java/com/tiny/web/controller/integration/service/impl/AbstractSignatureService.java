package com.tiny.web.controller.integration.service.impl;

import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.entity.FixedFax;
import com.tiny.web.controller.integration.service.SignatureService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

public abstract class AbstractSignatureService implements SignatureService {

    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(AbstractSignatureService.class);

    @PostConstruct
    protected void init() {
        logger.info("init ... ");
    }

    @Override
    public BaseJsonResult scan(String imagePath, String imageName) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        Long beginAt = System.currentTimeMillis();
        try {
            scanBefore(imagePath, imageName);
            SignatureResp signatureResp = processScan(imagePath, imageName);
            if (signatureResp == null || CollectionUtils.isEmpty(signatureResp.getMatchArea())) {
                throw new RuntimeException("no matched area ... ");
            }

            scanAfter(imagePath, imageName);
            baseJsonResult.markeSuccess("Congratulations, signature matched!", signatureResp);
        } catch (Exception e) {
            logger.error("scan error imagePath:" + imagePath, e);
            baseJsonResult.marketFail("Oops, " + e.getMessage());
        } finally {
            logger.info("scan total used:" + (System.currentTimeMillis() - beginAt));
        }
        return baseJsonResult;
    }

    @Override
    public BaseJsonResult fix(FixedFax fixedFax) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        Long beginAt = System.currentTimeMillis();
        try {
            fixBefore(fixedFax);

            processFix(fixedFax, baseJsonResult);

            fixAfter(fixedFax);

        } catch (Exception e) {
            logger.error("fix error fixedFax:" + fixedFax, e);
            baseJsonResult.marketFail("Oops, " + e.getMessage());
        } finally {
            logger.info("fix total used:" + (System.currentTimeMillis() - beginAt));
        }
        return baseJsonResult;
    }

    /**
     * @param imagePath
     * @param imageName
     */
    protected abstract void scanBefore(String imagePath, String imageName);

    /**
     * @param imagePath
     * @param imageName
     * @return
     */
    protected abstract SignatureResp processScan(String imagePath, String imageName);

    /**
     * @param imagePath
     * @param imageName
     */
    protected abstract void scanAfter(String imagePath, String imageName);

    /**
     * @param fixedFax
     */
    protected abstract void fixBefore(FixedFax fixedFax);

    /**
     * @param fixedFax
     */
    protected abstract void fixAfter(FixedFax fixedFax);

    /**
     * @param fixedFax
     * @param baseJsonResult
     */
    protected abstract void processFix(FixedFax fixedFax, BaseJsonResult baseJsonResult);


}
