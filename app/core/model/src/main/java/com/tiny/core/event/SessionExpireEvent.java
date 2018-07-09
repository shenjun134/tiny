/**
 * SessionExpireEvent.java
 *
 *
 */
package com.tiny.core.event;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SessionExpireEvent extends AbstractEvent {

	/**
	 * serialVersionUID
	 */
	private static final long	serialVersionUID	= 5735072407357702782L;

	private String				sessionId;

	/**
	 * 
	 */
	private String				userId;

	/**
	 * @author e521907
	 * @version 1.0
	 *
	 */
	private interface Constant {
		String	GROUP	= "SESSION";

		String	NAME	= "EXPIRE";

	}

	/**
	 * @param source
	 */
	public SessionExpireEvent(Object source) {
		super(source);
	}

	/**
	 * @param source
	 * @param userId
	 * @param sesssionId
	 */
	public SessionExpireEvent(Object source, String userId, String sesssionId) {
		super(source);
		this.userId = userId;
		this.sessionId = sesssionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.event.AbstractEvent#group()
	 */
	@Override
	protected String group() {
		return Constant.GROUP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.event.AbstractEvent#name()
	 */
	@Override
	protected String name() {
		return Constant.NAME;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
