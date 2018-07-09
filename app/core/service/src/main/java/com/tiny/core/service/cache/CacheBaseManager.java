/**
 * CacheBaseManager.java
 *
 */
package com.tiny.core.service.cache;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface CacheBaseManager {

	/**
	 * @param key
	 * @param value
	 * @param expireTime
	 * @param defaultExpireTime
	 */
	public void addCache(String key, Object value, long expireTime, long defaultExpireTime);

	/**
	 * @param key
	 */
	public void removeCache(String key);

	/**
	 * @param key
	 * @return
	 */
	public Object getCache(String key);

}
