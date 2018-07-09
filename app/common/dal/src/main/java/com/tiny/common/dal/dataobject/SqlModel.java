/**
 * SqlModel.java
 *
 */
package com.tiny.common.dal.dataobject;


/**
 * @author e521907
 * @version 1.0
 *
 */
public class SqlModel {

	private String	id;

	private String	clazz;

	private String	value;

	private String	params;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
