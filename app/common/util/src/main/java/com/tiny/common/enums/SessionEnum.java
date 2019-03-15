/**
 * SessionEnum.java
 *
 *
 */
package com.tiny.common.enums;


/**
 * @author e521907
 * @version 1.0
 *
 */
public enum SessionEnum implements EnumBase {
	
	SESSION_USER("K_USEER"),

	SESSION_AUTHS("K_AUTHS"),

	IS_LOGIN("K_IS_LOGIN"),
	
	URL_CACHE_KEY("URL_CACHE_KEY"),

	;

	private String	code;

	private SessionEnum(String code) {
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

}
