/**
 * DalAspect.java
 *
 *
 */
package com.tiny.common.dal;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
@Aspect
public class DalAspect {
	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(DalAspect.class);

	/**
	 * CONTEXT
	 */
	private static final String	CONTEXT	= "lemon";

	/**
	 * timeAt
	 */
	private ThreadLocal<Long>	timeAt	= new ThreadLocal<Long>();

	@Before("execution(* com." + CONTEXT + ".common.dal.daointerface.*.*(..))")
	public void beforeDao(JoinPoint point) {
		setTimeAt(System.currentTimeMillis());
	}

	@After("execution(* com." + CONTEXT + ".common.dal.daointerface.*.*(..))")
	public void afterDao(JoinPoint point) {
		Long used = System.currentTimeMillis() - getTimeAt();
		LogUtil.info(logger, "[daointerface]{0}.{1}--total used:{2}(ms)", point.getSignature().getDeclaringType()
				.getName(), point.getSignature().getName(), used);
	}

	@Pointcut
	public void runDao() {

	}

	public void setTimeAt(Long time) {
		timeAt.set(time);
	}

	public Long getTimeAt() {
		return timeAt.get();
	}
}
