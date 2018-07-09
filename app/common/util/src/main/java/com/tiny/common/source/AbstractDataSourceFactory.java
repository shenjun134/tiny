/**
 * AbstractDataSourceFactory.java
 *
 * Dec 19, 2016 - 3:51:53 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractDataSourceFactory implements DataSourceFactory {

	/**
	 * 
	 */
	protected Map<String, String>	jdbcDriverClassMap;

	/**
	 * @param jdbcUrl
	 * @return
	 */
	protected String determineDriverName(String jdbcUrl) {
		String driverClass = null;
		try {
			Driver driver = DriverManager.getDriver(jdbcUrl);
			driverClass = driver.getClass().getName();
		} catch (SQLException e) {
		}
		if (driverClass == null) {
			String dbIndicator = StringUtils.substringBetween(jdbcUrl, ":");
			driverClass = jdbcDriverClassMap.get(dbIndicator);
		}
		return driverClass;
	}

	/**
	 * @return
	 */
	public Map<String, String> getJdbcDriverClassMap() {
		return jdbcDriverClassMap;
	}

	/**
	 * @param jdbcDriverClassMap
	 */
	public void setJdbcDriverClassMap(Map<String, String> jdbcDriverClassMap) {
		this.jdbcDriverClassMap = jdbcDriverClassMap;
	}
}
