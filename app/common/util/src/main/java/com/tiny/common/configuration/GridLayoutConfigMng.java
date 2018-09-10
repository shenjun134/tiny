package com.tiny.common.configuration;

import com.tiny.common.util.CommonUtil;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GridLayoutConfigMng extends AbstractConfiguration {

    private static final Logger logger = Logger.getLogger(GridLayoutConfigMng.class);

    private Properties properties = new Properties();

    private List<Configuration> configurationList = new ArrayList<Configuration>();

    private ReentrantReadWriteLock propertiesLock = new ReentrantReadWriteLock();

    private static final String FILE_SEPARATOR = "_";

    private static String configName = "/config/grid-layout";


    private AtomicBoolean isStarted = new AtomicBoolean(false);

    private PropertiesConfiguration propertiesConfiguration;

    public void initialize() {
        getInstance().initialize(configName);
    }

    /**
     *
     */
    private GridLayoutConfigMng() {
    }

    /**
     * For returning the internal singleton GridLayoutConfigMng.
     *
     * @return
     */
    public static GridLayoutConfigMng getInstance() {
        return GridLayoutConfigMng.GridLayoutConfigMngHolder.instance;
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
            Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
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
     *
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
            logger.info("Using " + resourceName + " for initializing GridLayoutConfigMng");
            propertiesConfiguration = new XmlPropertiesConfiguration(resourceName);
            add(propertiesConfiguration);
        } else {
            // then properties file if no XML
            resourceName = getFileFullName(configName, env, ".properties");
            if (CommonUtil.isResourceExist(resourceName)) {
                logger.info("Using " + resourceName + " for initializing GridLayoutConfigMng");
                propertiesConfiguration = new FilePropertiesConfiguration(resourceName);
                add(propertiesConfiguration);
            } else {
                logger.info("No configuration file found for framework " + configName);
            }
        }
        // add log manager
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


    public PropertiesConfiguration getPropertiesConfiguration() {
        return getInstance().propertiesConfiguration;
    }

    /**
     * For holding the singleton GridLayoutConfigMng.
     *
     * @author Jimmy Zhang
     */
    private static class GridLayoutConfigMngHolder {
        private static final GridLayoutConfigMng instance = new GridLayoutConfigMng();
    }

}
