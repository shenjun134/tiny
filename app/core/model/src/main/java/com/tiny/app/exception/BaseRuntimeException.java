/**
 * BaseRuntimeException.java
 *
 *
 */
package com.tiny.app.exception;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -540918530058500227L;

	/**
	 * @param message
	 * @param r
	 */
	public BaseRuntimeException(String message, Throwable r) {
		super(message, r);
	}

	/**
	 * @param message
	 */
	public BaseRuntimeException(String message) {
		super(message);
	}

}
