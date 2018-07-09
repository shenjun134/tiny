//package com.tiny.web.controller.ocr.service;
//
//import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.lept.PIX;
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core.Rect;
//import org.bytedeco.javacpp.tesseract.TessBaseAPI;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.SessionScope;
//
//public class TesseractService {
//    private final static Logger logger = Logger.getLogger(TesseractService.class);
//
//    private TessBaseAPI api;
//
//    // Initialize tesseract-ocr with English, without specifying tessdata path
//    public TesseractService() {
//        logger.info("TesseractService: " + this + " Thread: " +Thread.currentThread().getId());
//        this.api = new TessBaseAPI();
//        api.Init(null, "eng");
//    }
//
//    /*
//    public String recognize(PIX image, Rect rect) {
//        // Open input image with leptonica library
//        try {
//            api.SetImage(image);
//            api.SetRectangle(rect.x(), rect.y(), rect.width(), rect.height());
//            return api.GetUTF8Text().getString();
//        } finally {
//            if (api != null) {
//                api.Clear();
//            }
//            // lept.pixDestroy(image);
//        }
//    }
//
//    public String recognize(PIX image) {
//        // Open input image with leptonica library
//        try {
//            api.SetImage(image);
//            return api.GetUTF8Text().getString();
//        } finally {
//            if (api != null) {
//                api.Clear();
//            }
//            // lept.pixDestroy(image);
//        }
//    }
//    */
//
//    public String recognize(Mat mat) {
//        try {
//            api.SetImage(mat.data(), mat.size().width(), mat.size().height(), mat.channels(), (int) mat.step1());
//            // LeptUtils.
//            return api.GetUTF8Text().getString();
//        } finally {
//            if (api != null) {
//                api.Clear();
//            }
//        }
//    }
//
//    public String recognize(Mat mat, Rect rect) {
//        try {
//            api.SetImage(mat.data(), mat.size().width(), mat.size().height(), mat.channels(), (int) mat.step1());
//            api.SetRectangle(rect.x(), rect.y(), rect.width(), rect.height());
//            return api.GetUTF8Text().getString();
//        } finally {
//            if (api != null) {
//                api.Clear();
//            }
//        }
//    }
//
//    public void destory() {
//        api.End();
//    }
//}
