package com.tiny.web.controller.ocr.service;

import com.tiny.web.controller.ocr.model.Detect;
import com.tiny.web.controller.ocr.model.Field;
import com.tiny.web.controller.ocr.model.Template;
import com.tiny.web.controller.ocr.util.Constants;
//import com.tiny.web.controller.ocr.util.OCRUtil;
import com.tiny.web.controller.ocr.util.PropUtil;
import com.tiny.web.controller.ocr.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.DoublePointer;
//import org.bytedeco.javacpp.opencv_core;
//import org.bytedeco.javacpp.opencv_core.*;

import java.util.*;

import static com.tiny.web.controller.ocr.util.Constants.OCR_TEMPLATE;
//import static org.bytedeco.javacpp.opencv_core.*;
//import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
//import static org.bytedeco.javacpp.opencv_imgproc.*;

public class DetectService
{
    private final static Logger logger = Logger.getLogger(DetectService.class);

//    private Rect match;

    private Template template;

    private List<Detect> detectList;

//    private OCRService ocrService;

    public List<Detect> getDetectList() {
        return detectList;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

//    public OCRService getOcrService() {
//        return ocrService;
//    }
//
//    public void setOcrService(OCRService ocrService) {
//        this.ocrService = ocrService;
//    }

    public void setDetectList() {
        detectList = new ArrayList<Detect>();
        String dectectProp = PropUtil.load(OCR_TEMPLATE).get(template.getName() + ".Detect.Order");
        if (StrUtil.isNullOrEmtry(dectectProp))
            return;
        String[] detectOrders = StringUtils.remove(dectectProp, ' ').split(",");

        for (String order : detectOrders) {
            dectectProp = PropUtil.load(OCR_TEMPLATE).get(template.getName() + ".Detect." + order);
            if (StrUtil.isNullOrEmtry(dectectProp))
                continue;
            String[] detectArr = StringUtils.remove(dectectProp, ' ').split(",");
            Detect detect = new Detect(order);
            detect.setLocation(Integer.parseInt(detectArr[0]), Integer.parseInt(detectArr[1]), Integer.parseInt(detectArr[2]) - Integer.parseInt(detectArr[0]), Integer.parseInt(detectArr[3]) - Integer.parseInt(detectArr[1]));

            dectectProp = PropUtil.load(OCR_TEMPLATE).get(template.getName() + ".Detect." + order + ".Policy");
            detectArr = StringUtils.remove(dectectProp, ' ').split(",");
            Constants.DetectPolicy detectPolicy = Constants.DetectPolicy.valueOf(detectArr[0]);
            detect.setPolicy(detectPolicy);
            switch (detectPolicy)
            {
                case One:
                    break;
                case H:
                    if (detectArr.length >= 2)
                        detect.setHorizontalThreshold(detectArr[1]);
                    break;
                case V:
                    if (detectArr.length >= 2)
                        detect.setVerticalThreshold(detectArr[1]);
                    break;
                case H_V:
                    if (detectArr.length == 2)
                        detect.setHorizontalThreshold(detectArr[1]);
                    else if (detectArr.length >= 3)
                    {
                        detect.setHorizontalThreshold(detectArr[1]);
                        detect.setVerticalThreshold(detectArr[2]);
                    }
                    break;
                case V_H:
                    if (detectArr.length == 2)
                        detect.setVerticalThreshold(detectArr[1]);
                    else if (detectArr.length >= 3)
                    {
                        detect.setVerticalThreshold(detectArr[1]);
                        detect.setHorizontalThreshold(detectArr[2]);
                    }
                    break;
                case Save:
                    break;
            }
            detectList.add(detect);
        }
    }

//    public Rect matchRegion(IplImage tmp, IplImage src) {
//        Rect ret = null;
//        IplImage result = null;
//        try {
//            logger.info("Template:" + tmp.toString());
//            logger.info("Source:" + src.toString());
//
//            result = cvCreateImage(cvSize(src.width() - tmp.width() + 1, src.height() - tmp.height() + 1), IPL_DEPTH_32F, 1);
//
//            //cvZero(result);
//            // Match Template Function from OpenCV
//            cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);
//
//            DoublePointer min_val = new DoublePointer();
//            DoublePointer max_val = new DoublePointer();
//
//            CvPoint min_loc = new CvPoint();
//            CvPoint max_loc = new CvPoint();
//
//            cvMinMaxLoc(result, min_val, max_val, min_loc, max_loc, null);
//            ret = new Rect(max_loc.x(), max_loc.y(), tmp.width(), tmp.height());
//        } finally {
//            cvReleaseImage(tmp);
//            cvReleaseImage(src);
//            cvReleaseImage(result);
//        }
//        return ret;
//    }
//
//    public Detect adjust(Detect detect)
//    {
//        detect.setX(match.x() + (detect.getX() - template.getX()));
//        detect.setY(match.y() + (detect.getY() - template.getY()));
//        return detect;
//    }
//
//    public boolean match(Mat src, String templatePath) {
//        match = matchRegion(OCRUtil.toIplImage(templatePath), OCRUtil.toIplImage(src));
//        if (Math.abs(match.x() - template.getX()) <= template.getFloatX()
//                && Math.abs(match.y() - template.getY()) <= template.getFloatY()) {
//            return true;
//        }
//        return false;
//    }
//
//    public Map<String, Field> detectAll(Mat page) {
//        Map<String, Field> fieldMap = null;
//        if (match(page, template.getPath())) {
//            fieldMap = new HashMap<String, Field>();
//            for (Detect d : detectList) {
//                adjust(d);
//                logger.info("After adjust: " + d.toString());
//
//                switch (d.getPolicy()) {
//                    case One:
//                        fieldMap.putAll(ocrService.recognizeRegion(d));
//                        break;
//                    case H:
//                        fieldMap.putAll(ocrService.recognizeRegion_Once(d));
//                        break;
//                    case V:
//                        fieldMap.putAll(ocrService.recognizeRegion_Once(d));
//                        break;
//                    case H_V:
//                        fieldMap.putAll(ocrService.recognizeRegion_Twice(d));
//                        break;
//                    case V_H:
//                        fieldMap.putAll(ocrService.recognizeRegion_Twice(d));
//                        break;
//                    case Save:
//                        // ocr.saveRegion(page, d.getName(), detect);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            // ocr.destory();
//
//            // if (fieldMap != null && fieldMap.size() > 0)
//            // FileUtil.writeToFile(ps.getPage(), StrUtil.mapToString(fieldMap),
//            // page);
//        }
//        return fieldMap;
//    }
}
