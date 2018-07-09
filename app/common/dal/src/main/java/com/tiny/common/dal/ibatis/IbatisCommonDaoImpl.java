/**
 * IbatisCommonDaoImpl.java
 *
 * Oct 20, 2016 - 1:06:18 PM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.ibatis;

import org.apache.log4j.Logger;

import com.tiny.common.dal.daointerface.IbatisCommonDao;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class IbatisCommonDaoImpl extends AbstractDaoSupport implements IbatisCommonDao {

	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(IbatisCommonDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisCommonDao#queryUserSequence()
	 */
	@Override
	public Long queryUserSequence() {
		LogUtil.debug(logger, "begin to queryUserSequence... ");
		return (Long) sqlTemplate().queryForObject(NAMESPACE + "get-user-sequence");
	}

}
