/**
 * SystemUtils.java
 *
 *
 */
package com.tiny.common.util;

import org.apache.commons.lang.StringUtils;

import com.tiny.common.configuration.ConfigurationManager;
import com.tiny.common.enums.EnvEnum;
import com.tiny.common.enums.SystemPropertyEnum;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SystemUtils {

	/**
	 * @param key
	 * @return
	 */
	public static String getSystemProperty(String key) {
		return ConfigurationManager.getInstance().getString(key);
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getSystemProperty(SystemPropertyEnum propertyEnum) {
		return getSystemProperty(propertyEnum.getKey());
	}

	/**
	 * @param key
	 * @return
	 */
	public static Integer getSystemPropertyAsInt(String key) {
		return ConfigurationManager.getInstance().getInteger(key);
	}

	/**
	 * @return
	 */
	public static String getContextName() {
		return getSystemProperty(SystemPropertyEnum.CONTEXT_NAME);
	}

	/**
	 * @return
	 */
	public static String getHomePage() {
		return getSystemProperty(SystemPropertyEnum.HOME_PAGE);
	}

	/**
	 * @return
	 */
	public static String getHomePageWithoutContext() {
		String homePage = getHomePage();
		String context = getContextName();
		if (StringUtils.contains(homePage, context)) {
			homePage = StringUtils.substring(homePage, homePage.indexOf(context) + context.length());
		}
		return homePage;
	}

	/**
	 * @return
	 */
	public static EnvEnum getEnv() {
		return EnvEnum.codeOf(getSystemProperty(SystemPropertyEnum.ENV));
	}

	/**
	 * @return
	 */
	public static String getSaltKey() {
		return getSystemProperty(SystemPropertyEnum.SALT_KEY);
	}
}
