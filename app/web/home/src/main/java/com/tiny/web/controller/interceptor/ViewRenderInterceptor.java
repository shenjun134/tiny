/**
 * ViewRenderInterceptor.java
 */
package com.tiny.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tiny.common.util.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tiny.common.enums.LocationEnum;
import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.annotation.Location;
import com.tiny.web.controller.constant.WebConstant;

/**
 * @author e521907
 * @version 1.0
 */
public class ViewRenderInterceptor extends HandlerInterceptorAdapter {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(ViewRenderInterceptor.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LogUtil.debug(LOGGER, "[ViewRender]preHandle request:{0}", request);
//        if (handler instanceof HandlerMethod) {
//            Location location = ((HandlerMethod) handler).getMethod().getAnnotation(Location.class);
//            if (location != null) {
//                request.setAttribute(WebConstant.CURRENT_NAV, getValue(location.nav()));
//                request.setAttribute(WebConstant.CURRENT_MENU, getValue(location.menu()));
//            }
//            request.setAttribute(WebConstant.WEB_HOME_ENTRY, SystemUtils.getHomeEntryPage());
//            request.setAttribute(WebConstant.WEB_CONTEXT_PATH, request.getContextPath());
//        }
        return super.preHandle(request, response, handler);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        LogUtil.debug(LOGGER, "[ViewRender]postHandle request:{0}", request);
        if (handler instanceof HandlerMethod) {
            Location location = ((HandlerMethod) handler).getMethod().getAnnotation(Location.class);
            if (location != null) {
                modelAndView.addObject(WebConstant.CURRENT_NAV, getValue(location.nav()));
                modelAndView.addObject(WebConstant.CURRENT_MENU, getValue(location.menu()));
            }
        }
        if (modelAndView != null) {
            modelAndView.addObject(WebConstant.WEB_HOME_ENTRY, SystemUtils.getHomeEntryPage());
            modelAndView.addObject(WebConstant.WEB_CONTEXT_PATH, request.getContextPath());
            modelAndView.addObject(WebConstant.IS_WEB_HOME_PAGE, SystemUtils.isHomePage(request.getRequestURI()));
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * @param location
     * @return
     */
    private String getValue(LocationEnum location) {
        return location == null ? null : location.getCode();
    }

}
