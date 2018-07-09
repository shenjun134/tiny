/**
 * NameObjectModel.java
 *
 * Dec 6, 2016 - 11:16:38 AM
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
public class NameObjectModel extends ToString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7919997418613605146L;

	private String				name;

	private Object				obj;

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
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

}
