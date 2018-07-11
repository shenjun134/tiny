package com.tiny.web.controller.ocr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OCRCommonController extends OCRController {

    @RequestMapping(path = UrlCenter.OCR.HELP)
    public String helpPage(ModelMap model) {
        model.addAttribute("view_path", UrlCenter.OCR.HELP);
        return View.HELP;
    }

}
