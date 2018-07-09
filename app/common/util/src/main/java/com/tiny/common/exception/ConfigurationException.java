/**
 * ConfigurationException.java
 *
 *
 */
package com.tiny.common.exception;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class ConfigurationException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5803350595956790165L;

	public ConfigurationException(String message, Throwable r) {
		super(message, r);
	}

	public ConfigurationException(String message) {
		super(message);
	}

}
