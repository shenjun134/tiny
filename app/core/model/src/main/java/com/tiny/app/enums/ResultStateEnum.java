package com.tiny.app.enums;

import com.tiny.common.enums.EnumBase;


/**
 * @author e521907
 * @version 1.0
 *
 */
public enum ResultStateEnum implements EnumBase {

	SUCC("�ɹ�", true, 1),

	FAIL("ʧ��", false, 2), ;

	private String	message;

	private Boolean	succ;

	private int		value;

	/**
	 * 
	 * @param message
	 * @param succ
	 * @param value
	 */
	private ResultStateEnum(String message, Boolean succ, int value) {
		this.message = message;
		this.succ = succ;
		this.value = value;
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public static ResultStateEnum nameOf(String name) {
		for (ResultStateEnum obj : values()) {
			if (obj.name().equals(name)) {
				return obj;
			}
		}
		return null;
	}

	/** 
	 * @see com.yylc.app.enums.EnumBase#message()
	 */
	@Override
	public String message() {
		return message;
	}

	/** 
	 * @see com.yylc.app.enums.EnumBase#value()
	 */
	@Override
	public Number value() {
		return value;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 * 
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for property <tt>message</tt>.
	 * 
	 * @param message value to be assigned to property message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for property <tt>succ</tt>.
	 * 
	 * @return property value of succ
	 */
	public Boolean getSucc() {
		return succ;
	}

	/**
	 * Setter method for property <tt>succ</tt>.
	 * 
	 * @param succ value to be assigned to property succ
	 */
	public void setSucc(Boolean succ) {
		this.succ = succ;
	}

	/**
	 * Getter method for property <tt>value</tt>.
	 * 
	 * @return property value of value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter method for property <tt>value</tt>.
	 * 
	 * @param value value to be assigned to property value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
