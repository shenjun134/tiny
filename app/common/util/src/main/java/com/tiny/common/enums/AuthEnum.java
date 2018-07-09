/**
 * AuthEnum.java
 *
 *
 */
package com.tiny.common.enums;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum AuthEnum implements EnumBase {
	DEFAULT("DEFAULT", "DEFAULT"),

	GC_DASHBOARD("GC_DASHBOARD", "dashboard for goldcopy"),

	GC_ANALYSIS("GC_ANALYSIS", "analysis for goldcopy"),

	GC_REPORT("GC_REPORT", "report for goldcopy"),

	GC_EXPORT("GC_EXPORT", "export for goldcopy"),

	GC_COMPONENT("GC_COMPONENT", "component for goldcopy"),

	GC_TOOL("GC_TOOL", "tool for goldcopy"),

	GC_MQ("GC_MQ", "mq for goldcopy"),

	SYS_SETTINGS("SYS_SETTINGS", "settings"), ;

	private String	code;

	private String	message;

	/**
	 * @param code
	 * @param message
	 */
	private AuthEnum(String code, String message) {
		this.code = code;
		this.message = message;
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

}
