/**
 * LogHandlerInterceptorAdapter.java
 *
 *
 */
package com.tiny.web.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class LogHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

	/**
	 * LOGGER
	 */
	private static final Logger	LOGGER	= Logger.getLogger(LogHandlerInterceptorAdapter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[Log]preHandle request:" + request);
		}
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
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[Log]postHandle request:" + request);
		}
		super.postHandle(request, response, handler, modelAndView);
	}
}
