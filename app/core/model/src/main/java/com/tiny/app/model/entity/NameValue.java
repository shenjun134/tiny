/**
 * NameValue.java
 *
 * Sep 8, 2016 - 3:49:37 PM
 *
 * "lemon-core-model
 *
 */
package com.tiny.app.model.entity;

import com.tiny.common.base.ToString;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class NameValue extends ToString {

	/**
	 * serialVersionUID
	 */
	private static final long	serialVersionUID	= 7251534652198390114L;

	private String				name;

	private Object				value;

	/**
	 * @param name
	 * @param value
	 */
	public NameValue(String name, Object value) {
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
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
