/**
 * SessionUtils.java
 *
 *
 */
package com.tiny.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.support.SessionStatus;

import com.tiny.common.enums.SessionEnum;
import com.tiny.common.enums.SystemPropertyEnum;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SessionUtils {

	private interface Constant {
		int	DEFAULT_SESSION_TIME	= 30 * 60;
	}

	private static Map<String, String>		cacheUrl		= new HashMap<String, String>();

	private static ReentrantReadWriteLock	cacheUrlLock	= new ReentrantReadWriteLock();

	/*
	 * @param request
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getPermisSet(HttpServletRequest request) {
		return (Set<String>) request.getSession().getAttribute(SessionEnum.SESSION_AUTHS.getCode());
	}

	/**
	 * @param request
	 * @return
	 */
	public static Object getSessionUser(HttpServletRequest request) {
		return request.getSession().getAttribute(SessionEnum.SESSION_USER.getCode());
	}

	/**
	 * @param session
	 * @param userModel
	 */
	public static void setUpSession(HttpSession session, Object userModel, Object permisSet) {
		session.setMaxInactiveInterval(getMaxInactiveInterval());
		session.setAttribute(SessionEnum.SESSION_USER.getCode(), userModel);
		session.setAttribute(SessionEnum.SESSION_AUTHS.getCode(), permisSet);
	}

	/**
	 * @param session
	 * @param userId
	 * @param request
	 */
	public static void cleanupSession(String userId, HttpServletRequest request, SessionStatus status) {
		HttpSession session = request.getSession(false);
		session.setAttribute(SessionEnum.SESSION_USER.getCode(), null);
		session.setAttribute(SessionEnum.SESSION_AUTHS.getCode(), null);
		session.invalidate();
		status.setComplete();
	}

	/**
	 * @return
	 */
	public static int getMaxInactiveInterval() {
		try {
			return SystemUtils.getSystemPropertyAsInt(SystemPropertyEnum.SESSION_MAXINACTIVEINTERVAL.getKey());
		} catch (Exception e) {
			return Constant.DEFAULT_SESSION_TIME;
		}
	}

	/**
	 * @param key
	 * @param url
	 */
	public static void putUrl(String key, String url) {
		cacheUrlLock.writeLock().lock();
		try {
			cacheUrl.put(key, url);
		} finally {
			cacheUrlLock.writeLock().unlock();
		}
	}

	/**
	 * @param key
	 * @param url
	 */
	public static String getUrl(String key) {
		cacheUrlLock.readLock().lock();
		try {
			return cacheUrl.get(key);
		} finally {
			cacheUrlLock.readLock().unlock();
		}
	}

	/**
	 * @param key
	 * @param url
	 */
	public static void removeUrl(String key) {
		cacheUrlLock.writeLock().lock();
		try {
			cacheUrl.remove(key);
		} finally {
			cacheUrlLock.writeLock().unlock();
		}
	}
}
