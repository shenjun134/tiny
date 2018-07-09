/**
 * IbatisUserDaoImpl.java
 *
 * Oct 13, 2016 - 2:40:03 PM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.ibatis;

import org.apache.log4j.Logger;

import com.tiny.common.dal.daointerface.IbatisUserDao;
import com.tiny.common.dal.dataobject.UserDO;
import com.tiny.common.util.LogUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class IbatisUserDaoImpl extends AbstractDaoSupport implements IbatisUserDao {

	/**
	 * logger
	 */
	private static final Logger	logger	= Logger.getLogger(IbatisUserDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisUserDao#queryUserByLoginId(java.lang.String)
	 */
	@Override
	public UserDO queryUserByLoginId(String loginId) {
		LogUtil.debug(logger, "begin to queryUserByLoginId loginId={0}... ", loginId);
		Object result = sqlTemplate().queryForObject(NAMESPACE + "select-user-by-loginid", loginId);
		if (result == null) {
			return null;
		}
		return (UserDO) result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisUserDao#queryUserByUserId(java.lang.Long)
	 */
	@Override
	public UserDO queryUserByUserId(Long userId) {
		LogUtil.debug(logger, "begin to queryUserByUserId userId={0}... ", userId);
		Object result = sqlTemplate().queryForObject(NAMESPACE + "select-user-by-userid", userId);
		if (result == null) {
			return null;
		}
		return (UserDO) result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisUserDao#insertUser(com.lemon.common.dal.dataobject.UserDO)
	 */
	@Override
	public int insertUser(UserDO DO) {
		LogUtil.debug(logger, "begin to insertUser UserDO={0}... ", DO);
		return sqlTemplate().update(NAMESPACE + "insert-user", DO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisUserDao#updateUser(com.lemon.common.dal.dataobject.UserDO)
	 */
	@Override
	public int updateUser(UserDO DO) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.common.dal.daointerface.IbatisUserDao#disableUser(java.lang.Long)
	 */
	@Override
	public int disableUser(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
