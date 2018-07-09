package com.tiny.web.controller.base;

import com.tiny.web.controller.integration.entity.FaxResult;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class ImagePost {

    public static void main(String[]  args){

        Long beginAt = System.currentTimeMillis();
        System.out.println("begin to...");
        try {
            process();
        }finally {

            System.out.println("end. with " + (System.currentTimeMillis() - beginAt));
        }


    }

    private static void process(){
        String url = "http://180.76.50.16:5000/fax";

        String imageName = "000000.jpg";
        String imagePath = "C:\\Users\\smile\\Pictures\\ocr-signatrue\\" + imageName;

        CloseableHttpClient httpClient = null;
        try{
            httpClient = HttpClients.custom().build();
            HttpPost httpPost = new HttpPost(url);

//            httpPost.setHeader("Content-Type", "image/jpg");

            FileBody fileBody = new FileBody(new File(imagePath), ContentType.create("image/jpg"));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

//            multipartEntityBuilder.addPart("file", fileBody);
//            multipartEntityBuilder.addBinaryBody("file", new File(imagePath));

            multipartEntityBuilder.addBinaryBody("file", new File(imagePath), ContentType.DEFAULT_BINARY, imageName);
            httpPost.setEntity(multipartEntityBuilder.build());

            HttpResponse response = httpClient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            String jsonResult = "";
            boolean sendSucc = statusLine != null && statusLine.getStatusCode() == 200;
            if(sendSucc){
                jsonResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            System.out.println(response);

            System.out.println("~~~~~~~~~~~~~~~~~");

            FaxResult faxResult = Json2Str.convert(jsonResult);

            System.out.println(faxResult);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
