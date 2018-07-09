package com.tiny.web.controller.enums;

import java.util.Date;

public enum ClazzEnum {
	BYTE(Byte.class, "1"),

	CHAR(Character.class, "1"),

	Boolean(Boolean.class, "1"),

	SHORT(Short.class, "2"),

	INTEGER(Integer.class, "4"),

	Long(Long.class, "8"),

	DOUBLE(Double.class, "8"),

	DATE(Date.class, "8"),

	String(String.class, "no limit"), ;

	private Class<?>	clazz;

	private String		length;

	ClazzEnum(Class<?> clazz, String length) {
		this.clazz = clazz;
		this.length = length;
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static ClazzEnum clazzOf(Class<?> clazz) {
		for (ClazzEnum temp : ClazzEnum.values()) {
			if (temp.clazz == clazz) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

}
