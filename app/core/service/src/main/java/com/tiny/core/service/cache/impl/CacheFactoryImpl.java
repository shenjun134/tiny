/**
 * CacheFactoryImpl.java
 *
 *
 */
package com.tiny.core.service.cache.impl;

import com.tiny.core.service.cache.CacheBaseManager;
import com.tiny.core.service.cache.CacheFactory;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class CacheFactoryImpl implements CacheFactory {

	/**
	 * 
	 */
	private CacheBaseManager	cacheManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.cache.CacheFactory#getCacheManager()
	 */
	@Override
	public CacheBaseManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheBaseManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
