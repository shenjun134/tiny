/**
 * SystemPropertiesConfiguration.java
 *
 *
 */
package com.tiny.common.configuration;

import com.tiny.common.util.CommonUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SystemPropertiesConfiguration extends PropertiesConfiguration {
	/**
	 * 
	 */
	public SystemPropertiesConfiguration() {
		properties = CommonUtil.retrieveSystemProperties();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean reload() {
		propertiesLock.writeLock().lock();
		try {
			properties = CommonUtil.retrieveSystemProperties();

			return true;
		} finally {
			propertiesLock.writeLock().unlock();
		}
	}

}
