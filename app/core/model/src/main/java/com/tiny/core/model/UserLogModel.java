/**
 * UserLogModel.java
 *
 * Oct 12, 2016 - 1:30:18 PM
 *
 * "lemon-core-model
 *
 */
package com.tiny.core.model;

import java.util.Date;

import com.tiny.common.base.ToString;
import com.tiny.common.enums.UserLogEnum;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class UserLogModel extends ToString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4753797741425272675L;

	private Date				createdAt;

	/**
	 * true: login, flase:logout
	 */
	private UserLogEnum			inOut;

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the inOut
	 */
	public UserLogEnum getInOut() {
		return inOut;
	}

	/**
	 * @param inOut the inOut to set
	 */
	public void setInOut(UserLogEnum inOut) {
		this.inOut = inOut;
	}

}
