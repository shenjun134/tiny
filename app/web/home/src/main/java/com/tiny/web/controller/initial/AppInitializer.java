/**
 * AppInitializer.java
 *
 *
 */
package com.tiny.web.controller.initial;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

import com.tiny.common.configuration.ConfigurationManager;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class AppInitializer implements WebApplicationInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		ConfigurationManager configurationManager = ConfigurationManager.getInstance();
		configurationManager.initialize();
	}

}
