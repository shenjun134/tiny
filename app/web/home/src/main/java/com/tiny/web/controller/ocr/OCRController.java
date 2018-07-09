package com.tiny.web.controller.ocr;

import com.tiny.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;

@Controller
public class OCRController extends BaseController {

    interface View {
        String PREFIX = "ocr/";
        String DEMO = PREFIX + "demo/index";
        String SIGNATURE = PREFIX + "signature/index";
        String HELP = PREFIX + "help/index";
    }


}
