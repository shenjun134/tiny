package com.tiny.web.controller.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.web.controller.integration.entity.FixedFax;

public class FunctionTest {

    public static void main(String[] args){
        FixedFax fixedFax = new FixedFax();
        fixedFax.setComments("fixed signature area");
        fixedFax.setFixedImageId("20180712120113-223.jpg");
        fixedFax.setWriterEmail("xx@xx.com");
        fixedFax.setWriterId(1L);
        fixedFax.setWriterName("Tian Sen");
        fixedFax.setHeight(210.91D);
        fixedFax.setWidth(320.1D);
        fixedFax.setXmin(23D);
        fixedFax.setYmin(45.2D);


        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(fixedFax);

        System.out.println(json);


    }
}
