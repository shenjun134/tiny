package com.tiny.common.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class ApplicationContextHelper implements ApplicationContextAware {

	/** ����Spring���applicationContext���� */
	private ApplicationContext				applicationContext;

	/** ��̬�� */
	private static ApplicationContextHelper	appContext;

	/**
	 * ˽�й���������ֹʵ��
	 */
	private ApplicationContextHelper() {
	}

	/**
	 * ����һ�������ʵ��
	 * 
	 * @return ApplicationContextHelper
	 */
	public static ApplicationContextHelper getInstance() {
		if (appContext == null) {
			appContext = new ApplicationContextHelper();
		}
		return appContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
