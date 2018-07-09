/**
 * yingyinglicai.com Inc. Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.tiny.app.enums;

import com.tiny.common.enums.EnumBase;

/**
 * �����ö��
 * 
 * @author dumao.qiu
 * @version $Id: ResultCodeEnum.java, v 0.1 2014��1��7�� ����12:10:51 QDM Exp $
 */
public enum ResultCodeEnum implements EnumBase {

	/** �����ɹ� */
	SUCCESS("SUCCESS", "�����ɹ�"),

	/** ��ݿ����ʧ�� */
	DB_ERROR("DB_ERROR", "��ݿ����ʧ�ܣ�"),

	/** ϵͳ�쳣 */
	SYSTEM_ERROR("SYSTEM_ERROR", "ϵͳ�쳣,����ϵ����Ա��"),

	/** �ⲿ�ӿڵ����쳣 */
	INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "ϵͳ�쳣,����ϵ����Ա��"),

	/** ϵͳ���� */
	SYSTEM_FAILURE("SYSTEM_FAILURE", "ϵͳ����"),

	/** ����Ϊ�� */
	NULL_ARGUMENT("NULL_ARGUMENT", "����Ϊ��"),

	/** �������쳣 */
	RESULT_PARSE_FAIL("RESULT_PARSE_FAIL", "�������쳣"),

	/** ��Ŀ���� */
	NUM_ERROR("NUM_ERROR", "���д��ص���Ŀ���ԣ�������ѡ�����"),

	/** ��ݴ��� */
	DATA_ERROR("DATA_ERROR", "���д��ص���ݲ���ȷ"),

	/** ��Ʒ����ҳ������쳣 */
	PRODUCT_ITEM_ERROR("PRODUCT_ITEM_ERROR", "��Ʒ����ҳ�����ʧ��,����ϵ����Ա��"),

	;

	/** ö�ٱ�� */
	private String	code;

	/** ö������ */
	private String	message;

	/**
	 * ������
	 * 
	 * @param code
	 * @param message
	 */
	private ResultCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * ͨ�������ȡö��
	 * 
	 * @param code
	 * @return
	 */
	public static ResultCodeEnum getResultCodeEnumByCode(String code) {
		for (ResultCodeEnum param : values()) {
			if (param.getCode().equals(code)) {
				return param;
			}
		}
		return null;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String message() {
		return this.message;
	}

	@Override
	public Number value() {
		return null;
	}

}
