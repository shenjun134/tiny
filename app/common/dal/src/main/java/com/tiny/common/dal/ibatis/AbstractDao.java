package com.tiny.common.dal.ibatis;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tiny.common.dal.dataobject.SqlModel;

public abstract class AbstractDao {

	/**
	 * LOGGER
	 */
	private static final Logger	LOGGER	= Logger.getLogger(AbstractDao.class);

	protected JdbcTemplate		jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param path
	 * @return
	 */
	public Map<String, SqlModel> store(String path) {
		// return LoadSqlUtils.retrieveDao(path);
		return null;
	}

	/**
	 * init by framework
	 */
	protected void init() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[AbstractDao]do some initialization work");
		}
	}

	/**
	 * destroy by framework
	 */
	protected void destroy() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[AbstractDao]do some destruction work");
		}
	}

}
