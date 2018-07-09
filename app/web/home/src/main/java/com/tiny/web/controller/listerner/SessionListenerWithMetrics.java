/**
 * SessionListenerWithMetrics.java
 *
 *
 */
package com.tiny.web.controller.listerner;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.tiny.common.enums.SessionEnum;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SessionListenerWithMetrics implements HttpSessionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		Object userModelObj = session.getAttribute(SessionEnum.SESSION_USER.getCode());
		if (userModelObj != null) {
			// UserModel userModel = (UserModel) userModelObj;
			// SessionExpireEvent expireEvent = new SessionExpireEvent(this, userModel.getLoginId(),
			// userModel.getSessionId());
			// AbstractPublisher.publishEvent(expireEvent);
		}
	}

}
