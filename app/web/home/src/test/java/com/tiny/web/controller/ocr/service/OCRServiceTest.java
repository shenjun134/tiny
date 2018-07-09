//package com.tiny.web.controller.ocr.service;
//
//
//import com.tiny.web.controller.ocr.model.Detect;
//import com.tiny.web.controller.ocr.model.Fax;
//import com.tiny.web.controller.ocr.model.Field;
//import com.tiny.web.controller.ocr.util.Constants;
//import com.tiny.web.controller.ocr.util.OCRUtil;
//import com.tiny.web.controller.ocr.util.StrUtil;
//import org.bytedeco.javacpp.opencv_core;
//import org.junit.Test;
//
//import java.util.Map;
//
//public class OCRServiceTest {
//
//    @Test
//    public void testRecognizeCover() throws Exception
//    {
//        Fax fax = new Fax();
//        fax.setPath("C:\\Users\\e521901\\Desktop\\fax\\sample\\REPLIX_FAX_2018030203307890_PIMCO_DERIVATIVE.tiff");
//        fax.split();
//
//        opencv_core.Mat cover = fax.getMatVector().get(0);
//        opencv_core.Mat erodeMat = OCRUtil.erode_Mat(cover);
//
//        Detect detectCover = new Detect("Cover");
//        detectCover.setPolicy(Constants.DetectPolicy.V);
//        detectCover.setLocation(0, 0, cover.size().width(), cover.size().height());
//        detectCover.setVerticalThreshold(5);
//
//        OCRService ocrService = new OCRService();
//        TesseractService tesseractService = new TesseractService();
//        ocrService.setPage(erodeMat);
//        ocrService.setTesseractService(tesseractService);
//        ocrService.setGrad(OCRUtil.convert(erodeMat));
//
//        Map<String, Field> coverFields = ocrService.recognizeCover(detectCover);
//        System.out.println(StrUtil.mapToString(coverFields));
//        OCRUtil.saveMat(erodeMat, "C:\\Users\\e521901\\Desktop\\fax\\sample\\11558907\\1.tiff");
//
//        coverFields = ocrService.recognizeCover(detectCover);
//        System.out.println(StrUtil.mapToString(coverFields));
//        OCRUtil.saveMat(erodeMat, "C:\\Users\\e521901\\Desktop\\fax\\sample\\11558907\\2.tiff");
//
//        coverFields = ocrService.recognizeCover(detectCover);
//        System.out.println(StrUtil.mapToString(coverFields));
//        OCRUtil.saveMat(erodeMat, "C:\\Users\\e521901\\Desktop\\fax\\sample\\11558907\\3.tiff");
//
//        tesseractService.destory();
//        fax.destory();
//    }
//}
