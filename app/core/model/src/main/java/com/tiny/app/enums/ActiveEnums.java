/**
 * ActiveEnums.java
 *
 * Oct 13, 2016 - 2:57:48 PM
 *
 * "lemon-core-model
 *
 */
package com.tiny.app.enums;

import org.apache.commons.lang.StringUtils;

import com.tiny.common.enums.EnumBase;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum ActiveEnums implements EnumBase {
	ON("Y"),

	OFF("N"), ;

	private String	code;

	private ActiveEnums() {

	}

	private ActiveEnums(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * use the code to get the ActiveEnums
	 * 
	 * @param code
	 * @return
	 */
	public static ActiveEnums codeOf(String code) {
		if (StringUtils.isBlank(code)) {
			return ActiveEnums.OFF;
		}
		for (ActiveEnums temp : values()) {
			if (StringUtils.equalsIgnoreCase(temp.getCode(), StringUtils.trim(code))) {
				return temp;
			}
		}
		return OFF;

	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

}
