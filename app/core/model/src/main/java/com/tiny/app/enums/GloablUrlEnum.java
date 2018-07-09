/**
 * GloablUrlEnum.java
 *
 *
 */
package com.tiny.app.enums;

import com.tiny.common.enums.EnumBase;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum GloablUrlEnum implements EnumBase {
	LOGIN_OUT("logout");

	;

	private String	url;

	private GloablUrlEnum(String url) {
		this.url = url;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
