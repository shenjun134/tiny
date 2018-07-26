package com.tiny.web.controller.integration.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.common.enums.SystemPropertyEnum;
import com.tiny.common.util.LogUtil;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.convert.FaxResultConverter;
import com.tiny.web.controller.integration.entity.FixedFax;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

@Component("remoteService")
public class RemoteSignatureService extends AbstractSignatureService {


    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(RemoteSignatureService.class);

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    @Override
    protected void scanBefore(String imagePath, String imageName) {
        logger.info("remote scanBefore check ...");
        Assert.hasText(SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_SIGNATURE_URL), "remote url is not setup");
        Assert.hasText(imagePath, "imagePath is blank");
        Assert.hasText(imageName, "imageName is blank");
        Assert.state(StringUtils.endsWith(imageName.toLowerCase(), ".jpg"), "Not a jpg image");
        File file = new File(imagePath + "/" + imageName);
        if (!file.exists()) {
            throw new IllegalArgumentException("Image is not existed.");
        }
        if (file.length() == 0) {
            throw new IllegalArgumentException("Image is blank.");
        }
        logger.info("remote scanBefore check end.");
    }

    @Override
    protected SignatureResp processScan(String imagePath, String imageName) {
        LogUtil.info(logger, "begin processScan with path: {0}, name: {1}", imagePath, imageName);
        CloseableHttpClient httpClient = null;
        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_SIGNATURE_URL);
        try {
            httpClient = HttpClients.custom().build();
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            String join = "/";
            if (StringUtils.endsWith(imagePath, "/") || StringUtils.endsWith(imagePath, "\\")) {
                join = "";
            }
            multipartEntityBuilder.addBinaryBody("file", new File(imagePath + join + imageName), ContentType.DEFAULT_BINARY, imageName);
            httpPost.setEntity(multipartEntityBuilder.build());

            HttpResponse response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            LogUtil.info(logger, "post image： {0} {1}, result: {2}", imagePath, imageName, statusLine);

            boolean sendSucc = statusLine != null && statusLine.getStatusCode() == 200;
            if (sendSucc) {
                logger.info("post successfully!");
                String jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");
                return FaxResultConverter.json2Signature(jsonResult);
            }
            throw new RuntimeException("image scan fail:" + statusLine);
        } catch (Exception e) {
            LogUtil.error(logger, e, "http post error with path: {0}, name: {1}, signatureUrl: {2}", imagePath, imageName, url);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("httpClient close error", e);
                }
            }
        }
    }

    @Override
    protected void scanAfter(String imagePath, String imageName) {
        logger.info("remote scanAfter done.");
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
    protected void processFix(FixedFax fixedFax, BaseJsonResult baseJsonResult) {
        LogUtil.info(logger, "begin processFix with path: {0}", fixedFax);
        CloseableHttpClient httpClient = null;
        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_SIGN_FIX_URL);
        try {
            httpClient = HttpClients.custom().build();
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            Gson gson = new GsonBuilder().create();

            String encoderJson = gson.toJson(fixedFax);
            StringEntity se = new StringEntity(encoderJson);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);

            HttpResponse response = httpClient.execute(httpPost);

            String jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");

            logger.info("post fix json response:" + jsonResult);

            StatusLine statusLine = response.getStatusLine();
            LogUtil.info(logger, "post fix json： {0}, result: {1}", fixedFax, statusLine);

            boolean sendSucc = statusLine != null && statusLine.getStatusCode() == 200;
            if (!sendSucc) {
                throw new RuntimeException("post fix json fail!");
            }
            baseJsonResult.markeSuccess(jsonResult, true);
        } catch (Exception e) {
            LogUtil.error(logger, e, "http post error with fixedFax: {0}, fix signatureUrl: {1}", fixedFax, url);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    logger.error("httpClient close error", e);
                }
            }
        }
    }

    @Override
    protected void fixAfter(FixedFax fixedFax) {
        logger.info("remote fix after done");
    }
}
