package com.tiny.web.controller.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.web.controller.integration.convert.FaxResultConverter;
import com.tiny.web.controller.integration.entity.FaxResult;

public class Json2Str {

    public static void main(String[] args){
        String jsonStr = "{\"faxes\": [{\"width\": 2480, \"signatures\": [{\"writers\": [{\"probability\": 0.64093435, \"writer_id\": 463}, {\"probability\": 0.14158027, \"writer_id\": 331}, {\"probability\": 0.08392442, \"writer_id\": 353}, {\"probability\": 0.036014844, \"writer_id\": 218}, {\"probability\": 0.035545222, \"writer_id\": 453}], \"height\": 112.663574, \"width\": 480.63513, \"score\": 0, \"xmin\": 725.6549, \"ymin\": 3236.686}], \"fax_id\": \"000000\", \"height\": 3508}], \"page\": 1}";


//        System.out.print(convert(jsonStr));

        System.out.print(FaxResultConverter.json2Signature(jsonStr));
    }

    public static FaxResult convert(String jsonStr){
        Gson gson = new GsonBuilder().create();

        return gson.fromJson(jsonStr, FaxResult.class);
    }
}
