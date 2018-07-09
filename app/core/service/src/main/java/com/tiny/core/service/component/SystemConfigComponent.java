/**
 * SystemConfigComponent.java
 *
 * Sep 8, 2016 - 3:39:27 PM
 *
 * "lemon-core-service
 *
 */
package com.tiny.core.service.component;

import java.util.List;

import com.tiny.app.model.entity.NameValue;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface SystemConfigComponent {

	/**
	 * 
	 */
	String	CACHE_KEY_SYS_CONF	= "CACHE_KEY_SYS_CONF";

	/**
	 * load the properties from VM
	 * 
	 * @return
	 */
	List<NameValue> loadPropertis();
}
