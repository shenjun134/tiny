/**
 * AbstractDaoSupport.java
 *
 *
 */
package com.tiny.common.dal.ibatis;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tiny.common.source.CollectSqlMapClientProvider;
import com.tiny.common.source.DataSourceSwitchHandler;
import com.tiny.common.util.DateUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class AbstractDaoSupport {

	/**
	 * sqlMapClient
	 */
	// private SqlMapClient sqlMapClient;

	private CollectSqlMapClientProvider	collectSqlMapClientProvider;

	public void init() {
		// setSqlMapClient(sqlMapClient);
	}

	public String formatDate(Date date) {
		return DateUtils.getNewFormatDateString(date);
	}

	/**
	 * @return
	 */
	public SqlMapClientTemplate sqlTemplate() {
		return getDefaultDaoSupport().getSqlMapClientTemplate();
	}

	/**
	 * @return
	 */
	public SqlMapClientTemplate dynamicSqlTemplate() {
		DataSourceSwitchHandler instance = DataSourceSwitchHandler.getInstance();
		String dbKey = instance.currentDataSource();
		if (StringUtils.isBlank(dbKey)) {
			return sqlTemplate();
		}
		return collectSqlMapClientProvider.retrieveSqlMapClient(dbKey).getSqlMapClientTemplate();
	}

	/**
	 * @return
	 */
	public SqlMapClientDaoSupport getDefaultDaoSupport() {
		return collectSqlMapClientProvider.retrieveDefault();
	}

	/**
	 * @param collectSqlMapClientProvider the collectSqlMapClientProvider to set
	 */
	public void setCollectSqlMapClientProvider(CollectSqlMapClientProvider collectSqlMapClientProvider) {
		this.collectSqlMapClientProvider = collectSqlMapClientProvider;
	}

}
