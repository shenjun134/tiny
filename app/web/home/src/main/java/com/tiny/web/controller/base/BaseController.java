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
            String SIGNATURE_VALIDATE_2 = "/ocr/signVal2.do";

            String SIGNATURE_SUBMIT = "/ocr/signSub.do";

            String BATCH_SIGNATURE_SUBMIT = "/ocr/batchSignSub.do";

            String BATCH_SIGNATURE_SUBMIT_2 = "/ocr/batchSignSub2.do";

            String SIGNATURE_LOAD = "/ocr/signLoad.do";
        }

        interface API {
            String LAYOUT_RECON = "/api/layout/recon.do";

            String DETAIL_RECON = "/api/detail/recon.do";

            String SUBMIT_RECON = "/api/detail/subRecon.do";

            String LAYOUT_RECON_2 = "/api/layout/recon2.do";

            String DETAIL_RECON_2 = "/api/detail/recon2.do";
        }

        interface Fax {
            String FAX_HOME = "/fax";
            String FAX_PREFIX = FAX_HOME + "/";
            String FAX_INDEX = FAX_PREFIX + "index";
            String FAX_ETD_INDEX = FAX_PREFIX + "etd";
            String FAX_ETD_VALIDATE = FAX_PREFIX + "etd/validate";
            String FAX_VALIDATE = FAX_PREFIX + "validate";
            String FAX_LAYOUT = FAX_PREFIX + "layout";
            String FAX_SIGNATURE = FAX_PREFIX + "signature";
            String FAX_CATEGORY = FAX_PREFIX + "category";
        }
    }

}
