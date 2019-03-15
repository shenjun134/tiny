/**
 * HBaseCommonController.java
 */
package com.tiny.web.controller.base;

import com.tiny.common.enums.LocationEnum;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.annotation.Location;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author e521907
 * @version 1.0
 */
@Controller
public class CommonController extends BaseController {

    interface View {
        String HELP = "help/index";
    }

    @RequestMapping(path = UrlCenter._403)
    public String forbidden() {
        return "/403";
    }

    @RequestMapping(path = UrlCenter._404)
    public String notFound() {
        return "/404";
    }

    @RequestMapping(path = UrlCenter._500)
    public String internalError() {
        return "/500";
    }

    @RequestMapping(path = {UrlCenter.ROOT, UrlCenter.INDEX, UrlCenter.INDEX + ".html", UrlCenter.INDEX + ".htm",
            UrlCenter.INDEX + ".do", UrlCenter.INDEX + ".jsp"})
    public String index() {
        return "redirect:" + getHomePage();
    }

    @RequestMapping(path = {UrlCenter.HOME, "/"})
    @Location(nav = LocationEnum.HOME, menu = LocationEnum.HOME)
    public void home() {
    }

    /**
     * @return
     */
    private String getHomePage() {
        String home = SystemUtils.getHomePage();
        String context = SystemUtils.getContextName();
        if (StringUtils.startsWith(home, "/" + context)) {
            return home.substring(("/" + context).length(), home.length());
        }
        return home;
    }
}
