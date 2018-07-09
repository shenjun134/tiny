/**
 * NameValueModel.java
 *
 * Dec 6, 2016 - 11:15:45 AM
 *
 * "lemon-core-model
 *
 */
package com.tiny.core.model;

import com.tiny.common.base.ToString;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class NameValueModel extends ToString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1823945658648573046L;

	private String				name;

	private String				value;

	public NameValueModel() {
		super();
	}

	public NameValueModel(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

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
