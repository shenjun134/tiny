/**
 * EnvEnum.java
 *
 *
 */
package com.tiny.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum EnvEnum implements EnumBase {
	LOCAL("LOCAL", "local environment", 0),

	SIT("SIT", "SIT environment", 10),

	UAT("UAT", "UAT environment", 100),

	BUAT("BUAT", "BUAT environment", 1000),

	PROD("PROD", "PROD environment", 10000),

	;

	/**
	 * 
	 */
	private String	code;

	/**
	 * 
	 */
	private String	message;

	/**
	 * 
	 */
	private Integer	id;

	private EnvEnum(String code, String message, Integer id) {
		this.code = code;
		this.message = message;
		this.id = id;
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

	/**
	 * @param code
	 * @return
	 */
	public static EnvEnum codeOf(String code) {
		if (StringUtils.isBlank(code)) {
			return LOCAL;
		}
		for (EnvEnum temp : values()) {
			if (StringUtils.equalsIgnoreCase(temp.getCode(), code)) {
				return temp;
			}
		}
		return LOCAL;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
