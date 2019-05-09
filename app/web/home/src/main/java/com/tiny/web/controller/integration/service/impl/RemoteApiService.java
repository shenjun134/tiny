package com.tiny.web.controller.integration.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.common.enums.SystemPropertyEnum;
import com.tiny.common.util.CommonUtil;
import com.tiny.common.util.LogUtil;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.convert.CommonConverter;
import com.tiny.web.controller.integration.entity.ContentResult;
import com.tiny.web.controller.integration.entity.GridLayoutResult;
import com.tiny.web.controller.integration.entity.LayoutResult;
import com.tiny.web.controller.integration.entity.LayoutWrapper;
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

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component("remoteApiService")
public class RemoteApiService extends AbstractApiService {

    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(RemoteApiService.class);

    private Properties layoutContentInterfaceMappingProp;

    @PostConstruct
    public void init() {
        String filename = "/config/ocr-layout-content-interface.properties";
        InputStream inputStream = null;
        try {
            inputStream = CommonUtil.getInputStream(filename);
            layoutContentInterfaceMappingProp = CommonUtil.retrieveFileProperties(inputStream);
        } catch (Exception e) {
            LogUtil.error(logger, e, "load {0} error", filename);
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogUtil.error(logger, e, "close {0} input stream error", filename);
                    throw new RuntimeException(e);
                }
            }
        }

    }


    @Override
    protected List<LayoutResult> doLayoutRecon(RecognitionReq request) {
        String imagePath = request.getImageAbsPath();
        String imageName = request.getReconImage();
        LogUtil.info(logger, "begin doLayoutRecon with path: {0}, name: {1}", imagePath, imageName);
        CloseableHttpClient httpClient = null;
        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_TEMPLATE_RECON_URL);
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
                return CommonConverter.parseLayoutResult(jsonResult);
            }
            throw new RuntimeException("image layout recon fail:" + statusLine);
        } catch (Exception e) {
            LogUtil.error(logger, e, "http post error with path: {0}, name: {1}, facade.template.recon.url: {2}", imagePath, imageName, url);
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
    protected LayoutWrapper doLayoutRecon2(RecognitionReq request) {
        String imagePath = request.getImageAbsPath();
        String imageName = request.getReconImage();
        LogUtil.info(logger, "begin doLayoutRecon with path: {0}, name: {1}", imagePath, imageName);
        CloseableHttpClient httpClient = null;
        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_TEMPLATE_RECON2_URL);
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
                LogUtil.info(logger, "{0}", jsonResult);
                return CommonConverter.parseLayoutResult2(jsonResult);
            }
            throw new RuntimeException("image layout recon fail:" + statusLine);
        } catch (Exception e) {
            LogUtil.error(logger, e, "http post error with path: {0}, name: {1}, facade.template.recon.url: {2}", imagePath, imageName, url);
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
    protected ContentResult doContentRecon(RecognitionReq request) {
        String imagePath = request.getImageAbsPath();
        String imageName = request.getReconImage();
        LogUtil.info(logger, "begin doContentRecon with path: {0}, name: {1}", imagePath, imageName);
        CloseableHttpClient httpClient = null;
//        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_OCR_RECON_URL);
        String url = getContentAPI(request.getLayoutId());
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
                return CommonConverter.parseContentResult(jsonResult);
            }
            throw new RuntimeException("image content recon fail:" + statusLine);
        } catch (Exception e) {
            LogUtil.error(logger, e, "http post error with path: {0}, name: {1}, facade.ocr.recon.url: {2}", imagePath, imageName, url);
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
    protected List<ContentResult> doContentRecon2(RecognitionReq request) {
        List<Map<String, Object>> reconLayoutList = new ArrayList<>();
        List<Map<String, Object>> newLayoutList = new ArrayList<>();
        String area_id = "";
        for (LayoutResult layout : request.getSelectedLayout()) {
            boolean isNew = Boolean.valueOf(layout.getNewArea());
            Map<String, Object> resultMap = convert(layout);
            if (isNew) {
                String id = "new-" + newLayoutList.size();
                resultMap.put("id", id);
                newLayoutList.add(resultMap);
                layout.setId(id);
            } else {
                reconLayoutList.add(resultMap);
                area_id = area_id + layout.getId() + " ";
            }
        }

        Map<String, Object> postBody = new HashMap<>();
//        postBody.put("layoutList", reconLayoutList);
//        postBody.put("addList", newLayoutList);
        postBody.put("area_id", area_id);
        postBody.put("fax_id", request.getLayoutId());
        postBody.put("new_add", newLayoutList);


        LogUtil.info(logger, "doContentRecon2 build post body - {0}", postBody);

        CloseableHttpClient httpClient = null;
        String url = SystemUtils.getSystemProperty(SystemPropertyEnum.FACADE_OCR_RECON2_URL);
        try {
            httpClient = HttpClients.custom().build();
            HttpPost httpPost = new HttpPost(url);
            Gson gson = new GsonBuilder().create();

            String encoderJson = gson.toJson(postBody);
            StringEntity stringEntity = new StringEntity(encoderJson);
            stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(httpPost);

            String jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");

            logger.info("post fix json response:" + jsonResult);

            StatusLine statusLine = response.getStatusLine();
            LogUtil.info(logger, "doContentRecon2 result: {0}", statusLine);

            boolean sendSucc = statusLine != null && statusLine.getStatusCode() == 200;
            if (!sendSucc) {
                throw new RuntimeException("doContentRecon2 fail! - " + statusLine);
            }
            List<ContentResult> results = CommonConverter.parseContentResult2(jsonResult);
            Map<String, ContentResult> resultMap = new HashMap<>();
            for(ContentResult contentResult : results){
                resultMap.put(contentResult.getResult().getId(), contentResult);
            }
            System.out.println("resultMap-" + resultMap.keySet());
//            results.forEach(item -> { resultMap.put(item.getId(), item); });

            for(LayoutResult layout : request.getSelectedLayout()){
                System.out.println("mapping-" + layout.getId());
                ContentResult temp = resultMap.get(layout.getId());
                System.out.println("temp-" + temp);
                if(temp != null){
                    temp.getResult().setX(layout.getX());
                    temp.getResult().setY(layout.getY());
                    temp.getResult().setWidth(layout.getWidth());
                    temp.getResult().setHeight(layout.getHeight());
                    continue;
                }
                ContentResult mocked = new ContentResult();
                mocked.setId(layout.getId());
                GridLayoutResult gridLayoutResult = new GridLayoutResult();
                gridLayoutResult.copy(layout);
                mocked.setResult(gridLayoutResult);
                results.add(mocked);
            }
            return results;

        } catch (Exception e) {
            LogUtil.error(logger, e, "doContentRecon2 error - request:{0}", request);
            throw new RuntimeException("doContentRecon2 error", e);
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
    protected void beforeLayout(RecognitionReq request) {
        commonAssert(request);
    }

    @Override
    protected void afterLayout(RecognitionReq request) {
        super.afterLayout(request);
    }

    @Override
    protected void beforeContent(RecognitionReq request) {
        commonAssert(request);
    }

    @Override
    protected void afterContent(RecognitionReq request) {
        super.afterContent(request);
    }

    /**
     * @param layoutId
     * @return
     */
    private String getContentAPI(String layoutId) {
        Assert.hasText(layoutId, "layout id is missing");
        String api = layoutContentInterfaceMappingProp.getProperty(layoutId);
        Assert.hasText(api, "layout Id cannot find content.api");
        return api;
    }

    private Map<String, Object> convert(LayoutResult layout) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", layout.getId());
//        map.put("width", layout.getWidth());
//        map.put("height", layout.getHeight());
        map.put("xmin", layout.getX());
        map.put("ymin", layout.getY());
        map.put("xmax", layout.getX() + layout.getWidth());
        map.put("ymax", layout.getY() + layout.getHeight());
        return map;
    }

}
