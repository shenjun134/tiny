/**
 * ComponentAction.java
 *
 * Sep 9, 2016 - 2:11:48 PM
 *
 * "lemon-web-home
 *
 */
package com.tiny.web.controller.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum ComponentAction {
	GENERATOR("generator", "Generator"),

	ZEROCLIP("zeroclip", "zeroclipboard"),

	;
	private String	code;

	private String	title;

	private ComponentAction() {

	}

	private ComponentAction(String code, String title) {
		this.code = code;
		this.title = title;
	}

	/**
	 * @param code
	 * @return
	 */
	public static ComponentAction codeOf(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (ComponentAction temp : values()) {
			if (StringUtils.equalsIgnoreCase(temp.code, code)) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
