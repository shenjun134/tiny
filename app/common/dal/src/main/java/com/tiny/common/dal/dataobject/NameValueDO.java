/**
 * NameValueDO.java
 *
 * Dec 6, 2016 - 11:29:14 AM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.dataobject;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class NameValueDO extends BaseDO {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4431199433959755956L;

	private String				name;

	private String				value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
