/**
 * DataSourceFactory.java
 *
 * Dec 19, 2016 - 3:51:15 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import javax.sql.DataSource;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface DataSourceFactory {
	/**
	 * @param dataSourceDefinition
	 * @return
	 */
	public DataSource getDataSource(DataSourceDefinition dataSourceDefinition);

	/**
	 * @param dataSource
	 */
	public void destroyDataSource(DataSource dataSource);
}
