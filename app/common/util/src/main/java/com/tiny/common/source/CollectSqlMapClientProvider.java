/**
 * CollectSqlMapClientProvider.java
 *
 * Dec 19, 2016 - 4:07:59 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tiny.common.util.AssertUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class CollectSqlMapClientProvider {

	/**
	 * 
	 */
	private Map<String, SqlMapClientDaoSupport>	sqlMapClientMap	= new HashMap<String, SqlMapClientDaoSupport>();

	private String								defaultKey;

	/**
	 * @param dbKey
	 * @return
	 */
	public SqlMapClientDaoSupport retrieveSqlMapClient(String dbKey) {
		AssertUtils.hasText(dbKey, "dbKey is empty");
		return sqlMapClientMap.get(dbKey);
	}

	/**
	 * @return
	 */
	public SqlMapClientDaoSupport retrieveDefault() {
		return retrieveSqlMapClient(defaultKey);
	}

	/**
	 * @param sqlMapClientMap
	 */
	public void setSqlMapClientMap(Map<String, SqlMapClientDaoSupport> sqlMapClientMap) {
		this.sqlMapClientMap = sqlMapClientMap;
	}

	/**
	 * @param defaultKey the defaultKey to set
	 */
	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

}
