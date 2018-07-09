/**
 * AuthInterceptor.java
 *
 *
 */
package com.tiny.web.controller.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tiny.common.enums.AuthEnum;
import com.tiny.common.enums.SessionEnum;
import com.tiny.common.util.CommonUtil;
import com.tiny.common.util.LogUtil;
import com.tiny.common.util.SessionUtils;
import com.tiny.web.controller.annotation.Auth;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	/**
	 * LOGGER
	 */
	private static final Logger		LOGGER	= Logger.getLogger(AuthInterceptor.class);

//	@Autowired
//	private AccountComponentFactory	loginComponentFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LogUtil.debug(LOGGER, "[Auth]postHandle request:{0}", request);
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		Object seesionUserObj = SessionUtils.getSessionUser(request);
		boolean isLogin = seesionUserObj != null;
		setAttribute(isLogin, seesionUserObj, request);
		if (!isLogin) {
			LogUtil.info(LOGGER, "[Auth]do not login with url:{0}", url);
			response.sendRedirect(contextPath + "/login");
			return false;
		}
		if (handler instanceof HandlerMethod) {
			Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
			if (auth != null && auth.authEnums() != null && auth.authEnums().length > 0) {
				Set<String> auths = SessionUtils.getPermisSet(request);
				if (!isAuthed(auths, auth.authEnums())) {
					response.sendRedirect(contextPath + "/403");
					return false;
				}
			}
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
		LogUtil.debug(LOGGER, "[Auth]postHandle request:{0}", request);
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * @param isLogin
	 * @param seesionUserObj
	 * @param request
	 */
	private void setAttribute(boolean isLogin, Object seesionUserObj, HttpServletRequest request) {
		if (isLogin) {
			request.setAttribute(SessionEnum.IS_LOGIN.getCode(), "true");
			request.setAttribute(SessionEnum.SESSION_USER.getCode(), seesionUserObj);
//			loginComponentFactory.getAccountComponent().loginActive(((UserModel) seesionUserObj).getLoginId());
		} else {
			request.setAttribute(SessionEnum.IS_LOGIN.getCode(), "false");
			cacheUrl(request);
		}
	}

	/**
	 * @param auths
	 * @param authEnums
	 * @return
	 */
	private boolean isAuthed(Set<String> auths, AuthEnum[] authEnums) {
		for (AuthEnum enumTemp : authEnums) {
			if (AuthEnum.DEFAULT == enumTemp) {
				return true;
			}
			if (auths.contains(enumTemp.getCode())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param contextPath
	 * @param url
	 */
	private void cacheUrl(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		String key = CommonUtil.createGuid() + "|" + System.currentTimeMillis();
		request.getSession().setAttribute(SessionEnum.URL_CACHE_KEY.getCode(), key);
		SessionUtils.putUrl(key, url);
	}

}
