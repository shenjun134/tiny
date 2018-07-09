/**
 * LogUtils.java
 *
 *
 */
package com.tiny.common.util;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * LoggerUtils
 * 
 * @author e521907
 * @version 1.0
 *
 */
final public class LogUtil {

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void debug(Logger logger, String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(MessageFormat.format(pattern, arguments));
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void debug(Logger logger, Throwable t, String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(MessageFormat.format(pattern, arguments), t);
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void warn(Logger logger, String pattern, Object... arguments) {
		logger.warn(MessageFormat.format(pattern, arguments));
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void warn(Logger logger, Throwable t, String pattern, Object... arguments) {
		logger.warn(MessageFormat.format(pattern, arguments), t);
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void info(Logger logger, String pattern, Object... arguments) {
		if (logger.isInfoEnabled()) {
			logger.info(MessageFormat.format(pattern, arguments));
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void info(Logger logger, Throwable t, String pattern, Object... arguments) {
		if (logger.isInfoEnabled()) {
			logger.info(MessageFormat.format(pattern, arguments), t);
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void error(Logger logger, Throwable t, String pattern, Object... arguments) {
		logger.error(MessageFormat.format(pattern, arguments), t);
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void error(Logger logger, String pattern, Object... arguments) {
		logger.error(MessageFormat.format(pattern, arguments));
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void trace(Logger logger, String pattern, Object... arguments) {
		if (logger.isTraceEnabled()) {
			logger.trace(MessageFormat.format(pattern, arguments));
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void trace(Logger logger, Throwable t, String pattern, Object... arguments) {
		if (logger.isTraceEnabled()) {
			logger.trace(MessageFormat.format(pattern, arguments), t);
		}
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param t
	 * @param arguments
	 */
	public static void fatal(Logger logger, Throwable t, String pattern, Object... arguments) {
		logger.fatal(MessageFormat.format(pattern, arguments), t);
	}

	/**
	 * @param logger
	 * @param pattern
	 * @param arguments
	 */
	public static void fatal(Logger logger, String pattern, Object... arguments) {
		logger.fatal(MessageFormat.format(pattern, arguments));
	}
}
