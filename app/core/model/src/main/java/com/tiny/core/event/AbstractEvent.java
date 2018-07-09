/**
 * AbstractEvent.java
 *
 *
 */
package com.tiny.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractEvent extends ApplicationEvent {

	/**
	 * @param source
	 */
	public AbstractEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 586584861962529459L;

	/**
	 * @return
	 */
	protected abstract String group();

	/**
	 * @return
	 */
	protected abstract String name();

}
