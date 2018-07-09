/**
 * DateRangEnum.java
 *
 *
 */
package com.tiny.common.enums;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum DateRangEnum implements EnumBase {
	ONE_DAY("ONE_DAY", "one day", 1, Calendar.DATE),

	TWO_DAY("TWO_DAY", "two days", 2, Calendar.DATE),

	THREE_DAY("THREE_DAY", "three days", 3, Calendar.DATE),

	FOUR_DAY("FOUR_DAY", "four days", 4, Calendar.DATE),

	FIVE_DAY("FIVE_DAY", "five days", 5, Calendar.DATE),

	SIX_DAY("SIX_DAY", "six days", 6, Calendar.DATE),

	ONE_WEEK("ONE_WEEK", "one week", 1, Calendar.WEEK_OF_MONTH),

	TWO_WEEK("TWO_WEEK", "two weeks", 2, Calendar.WEEK_OF_MONTH),

	THREE_WEEK("THREE_WEEK", "three weeks", 3, Calendar.WEEK_OF_MONTH),

	ONE_MONTH("ONE_MONTH", "one month", 1, Calendar.MONTH),

	TWO_MONTH("TWO_MONTH", "two months", 2, Calendar.MONTH),

	THREE_MONTH("THREE_MONTH", "three months", 3, Calendar.MONTH),

	ONE_YEAR("ONE_YEAR", "one year", 1, Calendar.YEAR),

	;

	private String	code;

	private String	message;

	private int		rang;

	private int		dateType;

	/**
	 * @param code
	 * @param message
	 * @param rang
	 * @param dateType
	 */
	private DateRangEnum(String code, String message, int rang, int dateType) {
		this.code = code;
		this.message = message;
		this.rang = rang;
		this.dateType = dateType;
	}

	/**
	 * @param code
	 * @return
	 */
	public static DateRangEnum codeOf(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		for (DateRangEnum temp : values()) {
			if (StringUtils.equalsIgnoreCase(code, temp.getCode())) {
				return temp;
			}
		}
		return null;

	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public int getRang() {
		return rang;
	}

	public int getDateType() {
		return dateType;
	}

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

}
