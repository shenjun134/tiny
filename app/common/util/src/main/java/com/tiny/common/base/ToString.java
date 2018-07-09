/**
 * 
 */
package com.tiny.common.base;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

/**
 * this class for override To string function
 * 
 * @see TO STRING
 * 
 * @author e521907
 *
 */
public class ToString implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long	serialVersionUID	= -7117122745267443709L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return buildString(this.getClass(), this);
	}

	/**
	 * build string with all the fields
	 * 
	 * @param clazz
	 * @param obj
	 * @return
	 */
	private String buildString(Class<?> clazz, Object obj) {
		StringBuilder builder = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		builder.append("[").append(clazz.getSimpleName()).append(":");
		for (Field temp : fields) {
			String name = temp.getName();
			if (StringUtils.contains("serialVersionUID", name)) {
				continue;
			}
			temp.setAccessible(true);
			Object value = null;
			try {
				value = temp.get(obj);
			} catch (Exception e) {
				//
			}
			builder.append(name).append("=").append(value).append(",");
		}
		String superClassName = clazz.getSuperclass().getSimpleName();
		if (!StringUtils.equalsIgnoreCase("Object", superClassName)
				&& !StringUtils.equalsIgnoreCase("ToString", superClassName)) {
			builder.append(buildString(clazz.getSuperclass(), obj));
		}
		String str = builder.toString();
		if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		str = str + "]";
		return str;
	}

}
