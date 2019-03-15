package com.tiny.web.controller.ocr;

import com.tiny.web.controller.integration.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FaxController extends OCRController {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(FaxController.class);

    interface View {
        String FAX_PREFIX = "fax/";
        String FAX_INDEX = FAX_PREFIX + "index";
        String FAX_ETD_INDEX = FAX_PREFIX + "etd/index";
        String FAX_ETD_VALIDATE = FAX_PREFIX + "etd/validate/index";
        String FAX_VALIDATE_INDEX = FAX_PREFIX + "validate/index";
        String FAX_LAYOUT = FAX_PREFIX + "layout";
        String FAX_SIGNATURE = FAX_PREFIX + "signature";
        String FAX_CATEGORY = FAX_PREFIX + "category";
    }

    @RequestMapping(path = {UrlCenter.Fax.FAX_HOME, UrlCenter.Fax.FAX_INDEX})
    public String indexView(ModelMap model, @RequestParam(value = "nodeChain", required = false) String nodeChain,
                            @RequestParam(value = "currentNode", required = false) String currentNode) {
        model.addAttribute("node-chain", nodeChain);
        model.addAttribute("current-node", currentNode);
        model.addAttribute("jump-url", UrlCenter.Fax.FAX_VALIDATE);
        return View.FAX_INDEX;
    }

    @RequestMapping(path = {UrlCenter.Fax.FAX_VALIDATE})
    public String validateView(ModelMap model, @RequestParam(value = "imgPath", required = false) String imgPath,
                               @RequestParam(value = "imgName", required = false) String imgName,
                               @RequestParam(value = "nodeChain", required = false) String nodeChain,
                               @RequestParam(value = "currentNode", required = false) String currentNode) {
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        model.addAttribute("img-path", imgPath);
        model.addAttribute("img-name", imgName);
        model.addAttribute("node-chain", nodeChain);
        model.addAttribute("current-node", currentNode);
        model.addAttribute("jump-url", UrlCenter.Fax.FAX_HOME);
        model.addAttribute("sign-validate-url", UrlCenter.OCR.SIGNATURE_VALIDATE);
        model.addAttribute("sign-fix-url", UrlCenter.OCR.BATCH_SIGNATURE_SUBMIT);
        return View.FAX_VALIDATE_INDEX;
    }


    @RequestMapping(path = {UrlCenter.Fax.FAX_ETD_INDEX})
    public String etdView(ModelMap model, @RequestParam(value = "nodeChain", required = false) String nodeChain,
                          @RequestParam(value = "currentNode", required = false) String currentNode) {
        model.addAttribute("node-chain", nodeChain);
        model.addAttribute("current-node", currentNode);
        model.addAttribute("jump-url", UrlCenter.Fax.FAX_ETD_VALIDATE);
        return View.FAX_ETD_INDEX;
    }

    @RequestMapping(path = {UrlCenter.Fax.FAX_ETD_VALIDATE})
    public String etdValidateView(ModelMap model, @RequestParam(value = "imgPath", required = false) String imgPath,
                                  @RequestParam(value = "imgName", required = false) String imgName,
                                  @RequestParam(value = "nodeChain", required = false) String nodeChain,
                                  @RequestParam(value = "currentNode", required = false) String currentNode) {
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        model.addAttribute("img-path", imgPath);
        model.addAttribute("img-name", imgName);
        model.addAttribute("node-chain", nodeChain);
        model.addAttribute("current-node", currentNode);
        model.addAttribute("jump-url", UrlCenter.Fax.FAX_ETD_INDEX);
        model.addAttribute("sign-validate-url", UrlCenter.OCR.SIGNATURE_VALIDATE_2);
        model.addAttribute("sign-fix-url", UrlCenter.OCR.BATCH_SIGNATURE_SUBMIT_2);
        return View.FAX_ETD_VALIDATE;
    }


    @RequestMapping(path = {UrlCenter.Fax.FAX_LAYOUT})
    public String layoutView(ModelMap model) {
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        return View.FAX_LAYOUT;
    }

    @RequestMapping(path = {UrlCenter.Fax.FAX_SIGNATURE})
    public String signatureView(ModelMap model) {
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        return View.FAX_SIGNATURE;
    }

    @RequestMapping(path = {UrlCenter.Fax.FAX_CATEGORY})
    public String categoryView(ModelMap model) {
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        return View.FAX_CATEGORY;
    }

}
