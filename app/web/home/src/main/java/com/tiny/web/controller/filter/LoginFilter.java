/**
 * LoginFilter.java
 *
 *
 */
package com.tiny.web.controller.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.http.request.EnhancedHttpRequest;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class LoginFilter implements Filter {

	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(LoginFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
																								ServletException {
		LogUtil.debug(logger, "[LoginFilter]preHandle request:{0}", request);
		String password = request.getParameter("password");
		if (StringUtils.isNotBlank(password)) {
			Map<String, String[]> additionalParams = new HashMap<String, String[]>();
			// String cryptedPassword = SaltedMD5Utils.getSecurePassword(password);
			String cryptedPassword = password;
			additionalParams.put("password", new String[] { cryptedPassword });
			EnhancedHttpRequest enhancedHttpRequest = new EnhancedHttpRequest((HttpServletRequest) request,
					additionalParams);
			// pass the request along the filter chain
			chain.doFilter(enhancedHttpRequest, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

}
