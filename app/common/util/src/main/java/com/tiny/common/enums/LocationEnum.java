/**
 * LocationEnum.java
 *
 *
 */
package com.tiny.common.enums;


/**
 * @author e521907
 * @version 1.0
 *
 */
public enum LocationEnum implements EnumBase {
	HOME("HOME", "HOME is nav"),

	GOLDCOPY("GOLDCOPY", "GOLDCOPY is nav"),

	OVERVIEW("OVERVIEW", "OVERVIEW is menu"),

	ANALYTICS("ANALYTICS", "ANALYTICS is menu"),

	REPORT("REPORT", "REPORT is menu"),

	EXPORT("EXPORT", "EXPORT is menu"),

	COMPONENT("COMPONENT", "COMPONENT is menu"),

	TOOL("TOOL", "TOOL is menu"),

	MQ("MQ", "MQ is menu"),

	SETTINGS("SETTINGS", "SETTINGS is nav"),

	SYS_CONF_MG("SYS_CONF_MG", "SYS_CONF_MG is menu"),

	SYS_RIGHTS_MG("SYS_RIGHTS_MG", "SYS_RIGHTS_MG is menu"),

	SYS_USER_MG("SYS_USER_MG", "SYS_USER_MG is menu"),

	SYS_URL_MG("SYS_URL_MG", "SYS_URL_MG is menu"),

	GAMES("GAMES", "GAMES is nav"),

	GAMES_DASHBOARD("GAMES_DASHBOARD", "GAMES_DASHBOARD is menu"),

	PROFILE("PROFILE", "PROFILE is nav"),

	PROFILE_INDEX("PROFILE_INDEX", "PROFILE_INDEX is menu"),

	PROFILE_LOG("PROFILE_LOG", "PROFILE_LOG is menu"),

	PROFILE_LETTER("PROFILE_LETTER", "PROFILE_LETTER is menu"),

	;

	private String	code;

	private String	message;

	private LocationEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public Number value() {
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
