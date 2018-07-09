package com.tiny.web.controller.integration.service.impl;

import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.service.SignatureService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

public abstract class AbstractSignatureService implements SignatureService {

    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(AbstractSignatureService.class);

    @PostConstruct
    protected void init(){
        logger.info("init ... ");
    }

    @Override
    public BaseJsonResult scan(String imagePath, String imageName) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        Long beginAt = System.currentTimeMillis();
        try{
            before(imagePath, imageName);
            SignatureResp signatureResp = process(imagePath, imageName);
            after(imagePath, imageName);
            baseJsonResult.markeSuccess("Congratulations, signature matched!", signatureResp);
        }catch (Exception e){
            logger.error("scan error imagePath:" + imagePath, e);
            baseJsonResult.marketFail("Oops, " + e.getMessage());
        }finally {
            logger.info("scan total used:" + (System.currentTimeMillis() - beginAt));
        }
        return baseJsonResult;
    }

    /**
     *
     * @param imagePath
     * @param imageName
     */
    protected abstract void before(String imagePath, String imageName);

    /**
     *
     * @param imagePath
     * @param imageName
     * @return
     */
    protected abstract SignatureResp process(String imagePath, String imageName);

    /**
     *
     * @param imagePath
     * @param imageName
     */
    protected abstract void after(String imagePath, String imageName);

}
