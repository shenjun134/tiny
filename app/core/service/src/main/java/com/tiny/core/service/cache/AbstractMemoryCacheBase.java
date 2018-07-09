/**
 * AbstractCacheBase.java
 *
 *
 */
package com.tiny.core.service.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.tiny.common.util.DateUtils;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractMemoryCacheBase implements CacheBaseManager {

	/**
	 * logger
	 */
	private static final Logger		logger				= Logger.getLogger(AbstractMemoryCacheBase.class);

	/**
	 * CACHE
	 */
	private Map<String, CacheData>	CACHE				= new HashMap<String, CacheData>();

	/**
	 * cacheLock
	 */
	private ReentrantReadWriteLock	cacheLock			= new ReentrantReadWriteLock();

	/**
	 * 5 mins
	 */
	private static final long		DEFAULT_EXPIRE_TIME	= 5 * 60 * 1000;

	/**
	 * 
	 */
	public void init() {
		CACHE.clear();
	}

	/**
	 * 
	 */
	public void cacheExpireCheck() {
		try {
			Iterator<Entry<String, CacheData>> cacheIterator = CACHE.entrySet().iterator();
			while (cacheIterator.hasNext()) {
				Entry<String, CacheData> entry = cacheIterator.next();
				boolean isExpired = entry.getValue().isExpired();
				LogUtil.debug(logger, "cache expire check---key:{0},isExpired:{1}", entry.getKey(), isExpired);
				if (isExpired) {
					cacheLock.writeLock().lock();
					try {
						cacheIterator.remove();
					} finally {
						cacheLock.writeLock().unlock();
					}
				}
			}
		} finally {
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.cache.CacheBase#addCache(java.lang.String, java.lang.Object, long, long)
	 */
	@Override
	public void addCache(String key, Object value, long expireTime, long defaultExpireTime) {
		cacheLock.writeLock().lock();
		try {
			CACHE.put(key, new CacheData(value, DateUtils.now(), expireTime > 0 ? expireTime : defaultExpireTime));
		} finally {
			cacheLock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.cache.CacheBase#removeCache(java.lang.String)
	 */
	@Override
	public void removeCache(String key) {
		cacheLock.writeLock().lock();
		try {
			CACHE.remove(key);
		} finally {
			cacheLock.writeLock().unlock();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.cache.CacheBase#getCache(java.lang.String)
	 */
	@Override
	public Object getCache(String key) {
		cacheLock.readLock().lock();
		try {
			CacheData data = CACHE.get(key);
			return data != null ? data.getData() : null;
		} finally {
			cacheLock.readLock().unlock();
		}

	}

	/**
	 * @author e521907
	 * @version 1.0
	 *
	 */
	public class CacheData {
		private Object	data;
		private Date	createAt;
		private long	expireTime;

		public CacheData() {

		}

		public CacheData(Object data, Date createAt, long expireTime) {
			this.data = data;
			this.createAt = createAt;
			this.expireTime = expireTime;
		}

		public boolean isExpired() {
			long runtime = Math.abs(DateUtils.now().getTime() - createAt.getTime());
			long expireT = expireTime > 0 ? expireTime : DEFAULT_EXPIRE_TIME;
			return runtime - expireT >= 0;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Date getCreateAt() {
			return createAt;
		}

		public void setCreateAt(Date createAt) {
			this.createAt = createAt;
		}

		public long getExpireTime() {
			return expireTime;
		}

		public void setExpireTime(long expireTime) {
			this.expireTime = expireTime;
		}
	}

}
