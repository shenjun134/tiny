/**
 * BonecpDataSourceFactory.java
 *
 * Dec 19, 2016 - 3:52:54 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class BonecpDataSourceFactory extends AbstractDataSourceFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.source.DataSourceFactory#getDataSource(com.lemon.common.source.DataSourceDefinition)
	 */
	@Override
	public DataSource getDataSource(DataSourceDefinition dataSourceDefinition) {
		String jdbcUrl = dataSourceDefinition.getJdbcUrl();
		String password = dataSourceDefinition.getPassword();
		String username = dataSourceDefinition.getUsername();

		BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
		boneCPDataSource.setDriverClass(determineDriverName(jdbcUrl));
		boneCPDataSource.setJdbcUrl(jdbcUrl);
		boneCPDataSource.setUsername(username);
		boneCPDataSource.setPassword(password);
		boneCPDataSource.setDefaultAutoCommit(false);
		// add more property here
		return boneCPDataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.source.DataSourceFactory#destroyDataSource(javax.sql.DataSource)
	 */
	@Override
	public void destroyDataSource(DataSource dataSource) {
		if (dataSource instanceof BoneCPDataSource) {
			((BoneCPDataSource) dataSource).close();
		}
	}

}
