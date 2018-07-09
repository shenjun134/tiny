/**
 * yingyinglicai.com Inc. Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.tiny.core.hold;

import com.tiny.core.model.WebUser;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class UserInfoHolder {

	public static ThreadLocal<WebUser>	userHolder	= new ThreadLocal<WebUser>();

	public static void put(WebUser user) {
		userHolder.set(user);
	}

	public static WebUser get() {
		return userHolder.get();
	}

}
