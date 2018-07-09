package com.tiny.common.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

public class PowerfulDateUtil {

	private static final Logger		LOGGER				= Logger.getLogger(PowerfulDateUtil.class);

	private static final String[]	INVALID_FORMAT		= new String[] { "000000", "00000000", "00/00/00", "00/00/0000" };

	private static final String[]	PATTERN_HH_MM		= new String[] { "HH", "mm" };

	private static final String[]	PATTERN_HH_MM_SS	= new String[] { "HH", "mm", "ss" };

	private static final String[]	PATTERN_YYYY_MMM_DD	= new String[] { "yyyy", "MMM", "dd" };

	private static final String[]	PATTERN_YY_MMM_DD	= new String[] { "yy", "MMM", "dd" };

	private static final String[]	PATTERN_YYYY_MM_DD	= new String[] { "yyyy", "MM", "dd" };

	private static final String[]	PATTERN_YY_MM_DD	= new String[] { "yy", "MM", "dd" };

	private static final String		AM_PM				= "a";

	private static final String		TIMEZONE			= "z";

	private static final String		SPACE				= " ";

	private static final String		EMPTY				= "";

	private static final String		COLON				= ":";

	private static final String		STRIPING			= "-";

	private static final String		UNDERLINE			= "_";

	private static final String		BACKSLASH			= "/";

	public static Date parse(String dateStr) throws ParseException {
		Long beginAt = System.nanoTime();
		dateStr = dateStrPreCheck(dateStr);
		String[] parsePatterns = getPatterns(dateStr);
		Date date = parse(dateStr, parsePatterns);
		if (LOGGER.isDebugEnabled()) {
			Long total = System.nanoTime() - beginAt;
			LOGGER.debug("Parse this DateStr(" + dateStr + ") total used:" + total + " nanoTime");
		}
		return date;
	}

	public static boolean isDate(String dateStr) {
		try {
			parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	private static String dateStrPreCheck(String dateStr) throws ParseException {
		if (StringUtils.isBlank(dateStr) || dateStr.trim().equalsIgnoreCase("null")) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("input value is empty.");
			}
			throw new ParseException("input value is empty.", 0);
		}
		dateStr = dateStr.trim();
		if (StringUtils.endsWithAny(dateStr, INVALID_FORMAT)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("date is invalid.");
			}
			throw new ParseException("date is invalid.", 0);
		}
		if (dateStr.length() == 9 && dateStr.startsWith("0")) {
			dateStr = dateStr.substring(1, dateStr.length());
		}
		return dateStr;
	}

	private static String[] getPatterns(String dateStr) {
		List<String> patternList = new ArrayList<String>();
		if (dateStr.indexOf(COLON) > 0) {
			if (dateStr.indexOf(STRIPING) > 0) {
				patternList.addAll(getTimePatternList(STRIPING));
			} else if (dateStr.indexOf(BACKSLASH) > 0) {
				patternList.addAll(getTimePatternList(BACKSLASH));
			} else if (dateStr.indexOf(UNDERLINE) > 0) {
				patternList.addAll(getTimePatternList(UNDERLINE));
			} else {
				patternList.addAll(getTimePatternList(SPACE));
				patternList.addAll(getTimePatternList(EMPTY));
			}
		} else if (StringUtils.isNumeric(dateStr)) {
			patternList.addAll(getPatterns(dateStr, EMPTY));
		} else if (dateStr.indexOf(BACKSLASH) > 0) {
			patternList.addAll(getPatterns(dateStr, BACKSLASH));
		} else if (dateStr.indexOf(STRIPING) > 0) {
			patternList.addAll(getPatterns(dateStr, STRIPING));
		} else if (dateStr.indexOf(UNDERLINE) > 0) {
			patternList.addAll(getPatterns(dateStr, UNDERLINE));
		} else {
			patternList.addAll(getPatterns(dateStr, SPACE));
			patternList.addAll(getPatterns(dateStr, EMPTY));
		}
		return patternList.toArray(new String[0]);
	}

	private static List<String> getPatterns(String dateStr, String interval) {
		return getDatePatterns(StringUtils.replaceChars(dateStr, interval, EMPTY), interval);
	}

	private static List<String> getDatePatterns(String dateStr, String interval) {
		List<String> patternList = new ArrayList<String>();
		if (StringUtils.isNumeric(dateStr)) {
			if (dateStr.length() == 6) {
				patternList.addAll(rankGroup(PATTERN_YY_MM_DD, interval));
			} else if (dateStr.length() == 8) {
				patternList.addAll(rankGroup(PATTERN_YYYY_MM_DD, interval));
			}
		} else {
			if (dateStr.length() == 7) {
				patternList.addAll(rankGroup(PATTERN_YY_MMM_DD, interval));
			} else if (dateStr.length() == 9) {
				patternList.addAll(rankGroup(PATTERN_YYYY_MMM_DD, interval));
			}
		}
		return patternList;
	}

	private static List<String> getTimePatternList(String interval) {
		List<String> patternList = new ArrayList<String>();
		patternList.addAll(getTimePatternList(PATTERN_YYYY_MMM_DD, interval));
		patternList.addAll(getTimePatternList(PATTERN_YY_MMM_DD, interval));
		patternList.addAll(getTimePatternList(PATTERN_YYYY_MM_DD, interval));
		patternList.addAll(getTimePatternList(PATTERN_YY_MM_DD, interval));
		return patternList;
	}

	private static List<String> getTimePatternList(String[] dates, String interval) {
		List<String> patternList = new ArrayList<String>();
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM));
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM_SS));
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM, AM_PM));
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM, TIMEZONE));
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM_SS, AM_PM));
		patternList.add(dateTimeJoin(dates, interval, PATTERN_HH_MM_SS, TIMEZONE));
		return patternList;
	}

	private static String dateTimeJoin(String[] dates, String interval, String[] times, String suffix) {
		return join(dates, interval) + join(times, COLON, suffix);
	}

	private static String dateTimeJoin(String[] dates, String interval, String[] times) {
		return join(dates, interval) + StringUtils.join(times, COLON);
	}

	private static String join(String[] args, String interval, String suffix) {
		return join(args, interval) + suffix;
	}

	private static String join(String[] args, String interval) {
		return StringUtils.join(args, interval) + SPACE;
	}

	private static List<String> rankGroup(String[] args, String interval) {
		List<String> group = new ArrayList<String>();
		Arrange anArrange = new Arrange(interval);
		group.addAll(anArrange.getArrangeList(args));
		return group;
	}

	private static Date parse(String dateStr, String[] partterns) throws ParseException {
		return DateUtils.parseDate(dateStr, partterns);
	}
}
