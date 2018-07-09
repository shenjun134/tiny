/**
 * AbstractTask.java
 *
 *
 */
package com.tiny.biz.task.base;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
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
public abstract class AbstractTask extends QuartzJobBean implements JobDetail {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3667761104525352511L;
	private ServiceTemplate		serviceTemplate;
	/**
	 * logger
	 */
	private static final Logger	logger				= Logger.getLogger(AbstractTask.class);

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
		((AbstractTask) context.getJobDetail().getJobDataMap().get(getTaskKey())).run();
	}

	/**
	 * run before
	 */
	protected void setup() {
		LogUtil.debug(logger, "set up....");
	}

	/**
	 * do the trigger logic
	 */
	protected abstract void start();

	/**
	 * @return
	 */
	protected abstract String getName();

	protected abstract String getTaskKey();

	/**
	 * @return
	 */
	protected abstract String getGroup();

	/**
	 * run after
	 */
	protected void teardown() {
		LogUtil.debug(logger, "teardown.");
	}

	public ServiceTemplate getServiceTemplate() {
		return serviceTemplate;
	}

	public void setServiceTemplate(ServiceTemplate serviceTemplate) {
		this.serviceTemplate = serviceTemplate;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public JobKey getKey() {
		return new JobKey(getName(), getGroup());
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Job> getJobClass() {
		return this.getClass();
	}

	@Override
	public JobDataMap getJobDataMap() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(getTaskKey(), this);
		return jobDataMap;
	}

	@Override
	public boolean isDurable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPersistJobDataAfterExecution() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConcurrentExectionDisallowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean requestsRecovery() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JobBuilder getJobBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

}
