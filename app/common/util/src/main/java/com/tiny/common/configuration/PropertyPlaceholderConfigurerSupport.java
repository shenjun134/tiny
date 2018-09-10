/**
 * PropertyPlaceholderConfigurerSupport.java
 */
package com.tiny.common.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.InputStreamResource;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PropertyPlaceholderConfigurerSupport extends PropertyPlaceholderConfigurer implements InitializingBean {

    /**
     * configurationManager
     */
    private ConfigurationManager configurationManager;

    /**
     *
     */
    public void init() {
        if (configurationManager != null) {
            configurationManager.initialize();
            setLocation(new InputStreamResource(configurationManager.getPropertiesConfiguration().getInputStream()));
        }
        GridLayoutConfigMng.getInstance().initialize();

    }

    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
