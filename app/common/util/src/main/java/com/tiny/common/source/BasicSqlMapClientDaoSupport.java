/**
 * BasicSqlMapClientDaoSupport.java
 *
 * Dec 20, 2016 - 11:32:45 AM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class BasicSqlMapClientDaoSupport extends SqlMapClientDaoSupport {

	/**
	 * sqlMapClient
	 */
	private SqlMapClient	sqlMapClient;

	public void init() {
		setSqlMapClient(sqlMapClient);
	}

	/**
	 * @return
	 */
	protected SqlMapClientTemplate sqlTemplate() {
		return getSqlMapClientTemplate();
	}

}
