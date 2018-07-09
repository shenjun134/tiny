package com.tiny.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ModelUtils {
	private static final String	POSTFIX			= "]";
	private static final String	PREFIX			= "[";
	private static final String	EQUALS			= "=";
	private static final String	EMPTY			= "";
	private static final int	INDEX_NOT_FOUND	= -1;

	public static <T> T cover2Object(Class<T> clazz, String toString) {
		String temp = getUsefulContent(toString);
		return newInstance(clazz, temp);
	}

	private static String getUsefulContent(String toString) {
		return substringBeforeLast(substringAfter(toString, PREFIX), POSTFIX);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	private static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	private static <T> T newInstance(Class<T> clazz, String temp) {
		T t = newInstance(clazz);
		setField(clazz, t, temp);
		return t;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			Constructor[] contructs = clazz.getConstructors();
			for (Constructor<?> cons : contructs) {
				Class[] types = cons.getParameterTypes();
				Object[] params = new Object[types.length];
				for (int i = 0; i < types.length; i++) {
					params[i] = newInstance(types[i]);
				}
				try {
					return (T) cons.newInstance(params);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setField(Class<?> clazz, Object t, String toString) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (simpleClassSet(field, toString, t) || collectionClassSet(field, toString, t)
					|| arrayClassSet(field, toString, t)) {
				continue;
			}
			complexClassSet(field, toString, t);
		}
		if (Object.class != clazz.getSuperclass()) {
			setField(clazz.getSuperclass(), t, toString);
		}
	}

	private static boolean arrayClassSet(Field field, String toString, Object t) {
		if (field.getType().isArray()) {
			// TODO
			return true;
		}
		return false;
	}

	private static boolean collectionClassSet(Field field, String toString, Object t) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			// TODO
			return true;
		}
		return false;
	}

	private static void complexClassSet(Field field, String toString, Object t) {
		if (isFieldExist(field, toString)) {
			try {
				String innerIoString = getActualContent(field, toString);
				field.set(t, newInstance(field.getType(), innerIoString));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	private static String getActualContent(Field field, String toString) {
		int index = getBeginIndex(field, toString);
		int endIndex = getTheMatchedPostFix(toString, index - 1);
		return toString.substring(index, endIndex);
	}

	private static int getTheMatchedPostFix(String toString, int beginAt) {
		Stack<Integer> preStack = new Stack<Integer>();
		for (int i = beginAt; i < toString.length(); i++) {
			if (PREFIX.equals(String.valueOf(toString.charAt(i)))) {
				preStack.push(Integer.valueOf(i));
			}
			if (POSTFIX.equals(String.valueOf(toString.charAt(i)))) {
				if (preStack.size() == 1) {
					return i;
				}
				preStack.pop();
			}
		}
		return toString.length() - 1;

	}

	private static int getBeginIndex(Field field, String toString) {
		return toString.indexOf(field.getName() + EQUALS) + (field.getName() + EQUALS).length()
				+ field.getType().getSimpleName().length() + 2;
	}

	private static boolean isFieldExist(Field field, String toString) {
		return toString.indexOf(field.getName() + EQUALS + field.getType().getSimpleName() + " ") > 0;
	}

	private static boolean simpleClassSet(Field field, String temp, Object t) {
		try {
			return doubleSet(field, temp, t) || floatSet(field, temp, t) || longSet(field, temp, t)
					|| charSet(field, temp, t) || intSet(field, temp, t) || byteSet(field, temp, t)
					|| shortSet(field, temp, t) || booleanSet(field, temp, t) || stringSet(field, temp, t)
					|| bigDataSet(field, temp, t) || atomicDataSet(field, temp, t) || dateSet(field, temp, t);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean doubleSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Double.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Double.valueOf(value));
			}
			return true;
		}
		if (double.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setDouble(t, Double.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean isNotBlank(String str) {
		return null != str && str.length() > 0 && !"null".equalsIgnoreCase(str);
	}

	private static boolean floatSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Float.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Float.valueOf(value));
			}
			return true;
		}
		if (float.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setFloat(t, Float.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean longSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Long.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Long.valueOf(value));
			}
			return true;
		}
		if (long.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setLong(t, Long.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean charSet(Field field, String temp, Object t) throws IllegalArgumentException,
																		IllegalAccessException {
		if (Character.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Character.valueOf(value.charAt(0)));
			}
			return true;
		}
		if (char.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setChar(t, Character.valueOf(value.charAt(0)));
			}
			return true;
		}
		return false;
	}

	private static boolean intSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Integer.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Integer.valueOf(value));
			}
			return true;
		}
		if (int.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setInt(t, Integer.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean byteSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Byte.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Byte.valueOf(value));
			}
			return true;
		}
		if (byte.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setByte(t, Byte.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean shortSet(Field field, String temp, Object t) throws NumberFormatException,
																		IllegalArgumentException,
																		IllegalAccessException {
		if (Short.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Short.valueOf(value));
			}
			return true;
		}
		if (short.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setInt(t, Short.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean booleanSet(Field field, String temp, Object t) throws IllegalArgumentException,
																			IllegalAccessException {
		if (Boolean.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, Boolean.valueOf(value));
			}
			return true;
		}
		if (boolean.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.setBoolean(t, Boolean.valueOf(value));
			}
			return true;
		}
		return false;
	}

	private static boolean stringSet(Field field, String temp, Object t) throws IllegalArgumentException,
																		IllegalAccessException {
		if (String.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, value);
			}
			return true;
		}
		return false;
	}

	private static boolean bigDataSet(Field field, String temp, Object t) throws NumberFormatException,
																			IllegalArgumentException,
																			IllegalAccessException {
		if (BigDecimal.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, BigDecimal.valueOf(Double.valueOf(value)));
			}
			return true;
		}

		if (BigInteger.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, BigInteger.valueOf(Integer.valueOf(value)));
			}
		}

		return false;
	}

	private static boolean atomicDataSet(Field field, String temp, Object t) throws NumberFormatException,
																			IllegalArgumentException,
																			IllegalAccessException {
		if (AtomicInteger.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, new AtomicInteger(Integer.valueOf(value)));
			}
			return true;
		}
		if (AtomicBoolean.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, new AtomicBoolean(Boolean.valueOf(value)));
			}
			return true;
		}

		if (AtomicLong.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, new AtomicLong(Long.valueOf(value)));
			}
			return true;
		}

		// if (AtomicReference.class != field.getType()) {
		// // TODO
		// return true;
		// }

		return false;

	}

	public static boolean dateSet(Field field, String temp, Object t) throws IllegalArgumentException,
																		IllegalAccessException {
		if (Date.class == field.getType()) {
			String value = getSampleValue(field.getName(), temp);
			if (isNotBlank(value)) {
				field.set(t, parseDate(value, new String[] { "E MMM dd HH:mm:ss z yyyy", "yyyy-MM-dd" }));
			}
			return true;
		}
		return false;
	}

	private static String getSampleValue(String fieldName, String temp) {
		String[] values = temp.split(", ");
		for (String value : values) {
			if (startsWith(value, fieldName + EQUALS, false)) {
				return substringAfter(value, fieldName + EQUALS);
			}
		}

		return null;
	}

	public static Date parseDate(String dateStr, String[] pattens) {
		for (String patten : pattens) {
			SimpleDateFormat format = new SimpleDateFormat(patten);
			try {
				return format.parse(dateStr);
			} catch (ParseException e) {
				continue;
			}
		}
		return null;
	}

	public static boolean isSampleType(Class<?> clazz) {
		Set<Class<?>> sampleClassSet = new HashSet<Class<?>>() {

			/**
			 * 
			 */
			private static final long	serialVersionUID	= 1L;
			{
				add(Double.class);
				add(Float.class);
				add(Integer.class);
				add(Long.class);
				add(Byte.class);
				add(Character.class);
				add(Short.class);
				add(Boolean.class);
				add(double.class);
				add(float.class);
				add(int.class);
				add(long.class);
				add(byte.class);
				add(char.class);
				add(short.class);
				add(boolean.class);
				add(String.class);
				add(Date.class);
				add(BigDecimal.class);
				add(BigInteger.class);
				add(Date.class);
			}

		};
		return sampleClassSet.contains(clazz);
	}
	
}
