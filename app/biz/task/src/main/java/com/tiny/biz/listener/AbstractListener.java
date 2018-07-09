/**
 * AbstractListener.java
 *
 *
 */
package com.tiny.biz.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.tiny.common.base.CommonResult;
import com.tiny.common.transaction.AbstractService;
import com.tiny.common.transaction.ServiceCheckCallback;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractListener<E extends ApplicationEvent> extends AbstractService implements
		ApplicationListener<E> {

	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(AbstractListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(final E event) {
		CommonResult<Boolean> baseResult = new CommonResult<Boolean>(true);
		serviceTemplate.executeWithoutTransaction(baseResult, new ServiceCheckCallback() {

			@Override
			public void check() {
				before(event);

			}

			@Override
			public void doTransaction() {
				handle(event);
				after(event);
			}
		});

	}

	/**
	 * @param event
	 */
	protected void before(E event) {
		LogUtil.debug(logger, "[AbstractListener] begin do before with event={0}... ", event);
	}

	/**
	 * @param event
	 */
	protected void after(E event) {
		LogUtil.debug(logger, "[AbstractListener] begin do after with event={0}...", event);
	}

	/**
	 * @param event
	 */
	protected abstract void handle(E event);

}
