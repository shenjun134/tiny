/**
 * InitializationException.java
 *
 *
 */
package com.tiny.common.exception;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class InitializationException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8136714700420261144L;

	public InitializationException(String message, Throwable r) {
		super(message, r);
	}

	public InitializationException(String message) {
		super(message);
	}

}
