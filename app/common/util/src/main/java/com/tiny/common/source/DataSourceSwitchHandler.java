/**
 * DataSourceSwitchHolder.java
 *
 * Dec 19, 2016 - 4:59:03 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

/**
 * @author e521907
 * @version 1.0
 *
 */
public final class DataSourceSwitchHandler {

	/**
	 * 
	 */
	private ThreadLocal<String>	datasourceKey	= new ThreadLocal<String>();

	/**
	 * @return
	 */
	public static DataSourceSwitchHandler getInstance() {
		return DataSourceSwitchHandlerHolder.instance;
	}

	/**
	 * @return the datasourceKey
	 */
	public ThreadLocal<String> getDatasourceKey() {
		return datasourceKey;
	}

	/**
	 * @param datasourceKey the datasourceKey to set
	 */
	public void setDatasourceKey(ThreadLocal<String> datasourceKey) {
		this.datasourceKey = datasourceKey;
	}

	/**
	 * @return
	 */
	public String currentDataSource() {
		return datasourceKey.get();
	}

	/**
	 * @param key
	 */
	public void putDataSourcekey(String key) {
		datasourceKey.set(key);
	}

	/**
	 * 
	 */
	public void removeDataSource() {
		datasourceKey.remove();
	}

	private static class DataSourceSwitchHandlerHolder {
		private static final DataSourceSwitchHandler	instance	= new DataSourceSwitchHandler();
	}

}
