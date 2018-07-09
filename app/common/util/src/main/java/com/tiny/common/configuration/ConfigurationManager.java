/**
 * ConfigurationManager.java
 *
 *
 */
package com.tiny.common.configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tiny.common.exception.InitializationException;
import com.tiny.common.util.CommonUtil;
import com.tiny.common.util.FileSupportUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class ConfigurationManager extends AbstractConfiguration {

	private static final Logger		logger				= Logger.getLogger(ConfigurationManager.class);

	private Properties				properties			= new Properties();

	private List<Configuration>		configurationList	= new ArrayList<Configuration>();

	private ReentrantReadWriteLock	propertiesLock		= new ReentrantReadWriteLock();

	private static final String		FILE_SEPARATOR		= "_";

	private static String			configName			= "/config/system";

	private static final String		CONFIG_LOG4J_XML	= "classpath:config/log4j.xml";

	private AtomicBoolean			isStarted			= new AtomicBoolean(false);

	private PropertiesConfiguration	propertiesConfiguration;

	public void initialize() {
		getInstance().initialize(configName);
	}

	/**
	 * 
	 */
	private ConfigurationManager() {
	}

	/**
	 * For returning the internal singleton ConfigurationManager.
	 * 
	 * @return
	 */
	public static ConfigurationManager getInstance() {
		return ConfigurationManagerHolder.instance;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean reload() {
		boolean isReloaded = false;
		propertiesLock.readLock().lock();
		try {
			for (Configuration configuration : configurationList) {
				if (configuration.reload()) {
					isReloaded = true;
				}
			}
			if (isReloaded) {
				updateProperties();
			}
		} finally {
			propertiesLock.readLock().unlock();
		}

		return isReloaded;
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
	@Override
	public boolean containsKey(String key) {
		propertiesLock.readLock().lock();
		try {
			return properties.containsKey(key);
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	/**
	 * @param configuration
	 */
	public void add(Configuration configuration) {
		propertiesLock.writeLock().lock();
		try {
			configurationList.add(configuration);
			updateProperties();
		} finally {
			propertiesLock.writeLock().unlock();
		}
	}

	/**
	 * @param configuration
	 */
	public void addFirst(Configuration configuration) {
		propertiesLock.writeLock().lock();
		try {
			configurationList.add(0, configuration);
			updateProperties();
		} finally {
			propertiesLock.writeLock().unlock();
		}
	}

	/**
	 * @return
	 */
	public int getConfigurationCount() {
		propertiesLock.readLock().lock();
		try {
			return configurationList.size();
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	@Override
	public Properties getProperties() {
		propertiesLock.readLock().lock();
		try {
			Properties newProperties = new Properties();
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				newProperties.put(entry.getKey(), entry.getValue());
			}

			return newProperties;
		} finally {
			propertiesLock.readLock().unlock();
		}
	}

	/**
	 * Initialize configuration manager for the system.
	 * 
	 * @param frameworkName
	 */
	public void initialize(String frameworkName) {
		initialize(frameworkName, null);
	}

	/**
	 * Initialize configuration manager for the system.
	 * @param configName
	 * @param env
	 */
	public void initialize(String configName, String env) {
		if (isStarted.get()) {
			return;
		}
		isStarted.set(true);
		// file based is the lowest priority
		String resourceName = getFileFullName(configName, env, ".xml");
		if (CommonUtil.isResourceExist(resourceName)) {
			// use XML in higher priority
			logger.info("Using " + resourceName + " for initializing configurationManager");
			propertiesConfiguration = new XmlPropertiesConfiguration(resourceName);
			add(propertiesConfiguration);
		} else {
			// then properties file if no XML
			resourceName = getFileFullName(configName, env, ".properties");
			if (CommonUtil.isResourceExist(resourceName)) {
				logger.info("Using " + resourceName + " for initializing configurationManager");
				propertiesConfiguration = new FilePropertiesConfiguration(resourceName);
				add(propertiesConfiguration);
			} else {
				logger.info("No configuration file found for framework " + configName);
			}
		}
		// add log manager
		loadLogConfig(CONFIG_LOG4J_XML);
		// then system properties
		add(new SystemPropertiesConfiguration());
		CommonUtil.logProperties(properties);
	}

	/**
	 * @param resourceName
	 * @param env
	 * @param fileType
	 * @return
	 */
	private String getFileFullName(String resourceName, String env, String fileType) {
		String combiner = "";
		if (env != null) {
			combiner = FILE_SEPARATOR + env;
		}
		resourceName = resourceName + combiner + fileType;
		return resourceName;
	}

	/**
	 * 
	 */
	private void updateProperties() {
		propertiesLock.writeLock().lock();
		try {
			Properties newProperties = new Properties();
			for (Configuration configuration : configurationList) {
				CommonUtil.mergeProperties(newProperties, configuration.getProperties());
			}

			this.properties = newProperties;
		} finally {
			propertiesLock.writeLock().unlock();
		}
	}

	private void loadLogConfig(String url) {
		ClassPathXmlApplicationContext tempContext = null;
		try {
			tempContext = new ClassPathXmlApplicationContext();
			String logConfig = FileSupportUtils.getString(tempContext.getResource(url).getFile()).toString();
			InputStream logInputStream = new ByteArrayInputStream(enrichVariables4LogConfig(logConfig)
					.getBytes("UTF-8"));
			OptionConverter.selectAndConfigure(logInputStream, "org.apache.log4j.xml.DOMConfigurator",
					LogManager.getLoggerRepository());
		} catch (Exception e) {
			throw new InitializationException("loadLogConfig error with url:" + url, e);
		} finally {
			if (tempContext != null) {
				tempContext.close();
			}
		}
	}

	/**
	 * @param logConfig
	 * @return
	 */
	private String enrichVariables4LogConfig(String logConfig) {
		String temp = logConfig;
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			temp = StringUtils.replace(temp, "${" + entry.getKey() + "}", String.valueOf(entry.getValue()));
		}
		return temp;
	}

	/**
	 * 
	 */
	public void cleanup() {
		propertiesLock.writeLock().lock();
		try {
			properties.clear();
			configurationList.clear();
		} finally {
			propertiesLock.writeLock().unlock();
		}
	}

	public static void setConfigName(String configName) {
		ConfigurationManager.configName = configName;
	}

	public PropertiesConfiguration getPropertiesConfiguration() {
		return getInstance().propertiesConfiguration;
	}

	/**
	 * For holding the singleton ConfigurationManager.
	 * 
	 * @author Jimmy Zhang
	 */
	private static class ConfigurationManagerHolder {
		private static final ConfigurationManager	instance	= new ConfigurationManager();
	}

}
