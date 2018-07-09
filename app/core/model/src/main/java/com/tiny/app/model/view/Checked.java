/**
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.tiny.app.model.view;

import com.tiny.common.base.ToString;

/**
 * �Ƿ�ѡ�е����
 * 
 * @author dumao.qiu
 * @version $Id: Checked.java, v 0.1 2013��12��25�� ����4:39:25 QDM Exp $
 */
public class Checked extends ToString {

	/** serialVersionUID */
	private static final long	serialVersionUID	= 156619422140272652L;

	/** id */
	protected long				id;

	/** ��� */
	protected String			name;

	/** ֵ */
	protected String			value;

	/** �Ƿ�ѡ�� */
	protected Boolean			checked;

	/** �Ƿ�ɷ��� */
	protected Boolean			assigned;

	/**
	 * ������
	 */
	public Checked() {
	}

	/**
	 * ������
	 * 
	 * @param id
	 * @param name
	 * @param value
	 * @param checked
	 */
	public Checked(long id, String name, String value, Boolean checked) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.checked = checked;
	}

	/**
	 * ������
	 * 
	 * @param id
	 * @param name
	 * @param value
	 * @param checked
	 * @param assigned
	 */
	public Checked(long id, String name, String value, Boolean checked, Boolean assigned) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.checked = checked;
		this.assigned = assigned;
	}

	/**
	 * Getter method for property <tt>id</tt>.
	 * 
	 * @return property value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>value</tt>.
	 * 
	 * @return property value of value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter method for property <tt>value</tt>.
	 * 
	 * @param value value to be assigned to property value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter method for property <tt>checked</tt>.
	 * 
	 * @return property value of checked
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * Setter method for property <tt>checked</tt>.
	 * 
	 * @param checked value to be assigned to property checked
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * Getter method for property <tt>assigned</tt>.
	 * 
	 * @return property value of assigned
	 */
	public Boolean getAssigned() {
		return assigned;
	}

	/**
	 * Setter method for property <tt>assigned</tt>.
	 * 
	 * @param assigned value to be assigned to property assigned
	 */
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}

}
