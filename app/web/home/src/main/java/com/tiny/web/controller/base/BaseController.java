package com.tiny.web.controller.base;

import org.springframework.stereotype.Controller;

/**
 * @author e521907
 * @version 1.0
 */
@Controller
abstract public class BaseController {


    /**
     * @author e521907
     * @version 1.0
     */
    public interface BaseConstant {
        String CURRENT_NAV = "CURRENT_NAV";

        String CURRENT_MENU = "CURRENT_MENU";
    }

    /**
     * @author e521907
     * @version 1.0
     */
    public interface UrlCenter {
        String ROOT = "/";

        String INDEX = "index";

        String _404 = "/404";

        String _403 = "/403";

        String _500 = "/500";

        String HOME = "/home";

        interface OCR {
            String HOME = "/ocr";
            String DEMO = "/ocr/demo";
            String SIGNATURE = "/ocr/signature";
            String ROTATE_IMAGE = "/ocr/image/rotate.do";

            String HELP = "/ocr/help";

            String FIND_IMAGE = "/ocr/find/image";

            String MULTI_UPLOAD = "/ocr/multiUpload.do";

            String SIGNATURE_UPLOAD = "/ocr/signUpload.do";

            String SIGNATURE_LOAD_SAMPLE_NAME = "/ocr/sign/sampleLoad";

            String COVER_SCAN = "/ocr/coverScan.do";

            String TEMP_SCAN = "/ocr/tempScan.do";

            String TEMP_SUB = "/ocr/tempSub.do";

            String SIGNATURE_VALIDATE = "/ocr/signVal.do";

            String SIGNATURE_SUBMIT = "/ocr/signSub.do";
        }
    }

}
