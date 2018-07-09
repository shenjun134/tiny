/**
 * PropertiesConfiguration.java
 *
 *
 */
package com.tiny.common.configuration;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.tiny.common.util.CommonUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PropertiesConfiguration extends AbstractConfiguration {
	protected Properties				properties		= null;

	protected ReentrantReadWriteLock	propertiesLock	= new ReentrantReadWriteLock();

	protected InputStream				inputStream;

	protected String					filename;

	protected URL						url;

	/**
	 * 
	 */
	public PropertiesConfiguration() {
	}

	/**
	 * @param properties
	 */
	public PropertiesConfiguration(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @param properties
	 */
	public PropertiesConfiguration(boolean isSilentMissing, Properties properties) {
		this(properties);

		setSilentMissing(isSilentMissing);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValue(String key) {
		propertiesLock.readLock().lock();
		try {
			return properties.getProperty(key);
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsKey(String key) {
		propertiesLock.readLock().lock();
		try {
			return properties.containsKey(key);
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean reload() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Properties getProperties() {
		propertiesLock.readLock().lock();
		try {
			Properties newProperties = new Properties();
			CommonUtil.mergeProperties(newProperties, properties);

			return newProperties;
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	public InputStream getInputStream() {
		propertiesLock.readLock().lock();
		try {
			if (filename != null) {
				inputStream = CommonUtil.getInputStream(filename);
			} else if (url != null) {
				inputStream = CommonUtil.getInputStream(url);
			}

			return inputStream;
		} finally {
			propertiesLock.readLock().unlock();
		}

	}
}
