/**
 * AppInitializer.java
 *
 *
 */
package com.tiny.common.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

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
