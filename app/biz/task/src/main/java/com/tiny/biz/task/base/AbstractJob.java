/**
 * AbstractTask.java
 *
 *
 */
package com.tiny.biz.task.base;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tiny.common.base.BaseResult;
import com.tiny.common.transaction.ServiceCallback;
import com.tiny.common.transaction.ServiceTemplate;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractJob extends QuartzJobBean {

	private ServiceTemplate		serviceTemplate;
	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(AbstractTask.class);

	protected void run() {
		LogUtil.debug(logger, "[AbstractTask]begin to run ...");
		BaseResult result = serviceTemplate.executeWithoutTransaction(new ServiceCallback() {

			@Override
			public void doTransaction() {
				setup();
				start();
				teardown();
			}
		});
		LogUtil.debug(logger, "run result:{0}", result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org .quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		run();
	}

	/**
	 * run before
	 */
	protected void setup() {
		LogUtil.debug(logger, "[AbstractTask] set up....");
	}

	/**
	 * do the trigger logic
	 */
	protected abstract void start();

	/**
	 * run after
	 */
	protected void teardown() {
		LogUtil.debug(logger, "[AbstractTask] teardown.");
	}

	public ServiceTemplate getServiceTemplate() {
		return serviceTemplate;
	}

	public void setServiceTemplate(ServiceTemplate serviceTemplate) {
		this.serviceTemplate = serviceTemplate;
	}

}
