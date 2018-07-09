/**
 * IbatisUserDao.java
 *
 * Oct 12, 2016 - 11:08:50 AM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.daointerface;

import com.tiny.common.dal.dataobject.UserDO;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface IbatisUserDao {

	String	NAMESPACE	= "user.";

	/**
	 * @param loginId
	 * @return
	 */
	public UserDO queryUserByLoginId(String loginId);

	/**
	 * @param userId
	 * @return
	 */
	public UserDO queryUserByUserId(Long userId);

	/**
	 * @param DO
	 * @return
	 */
	public int insertUser(UserDO DO);

	/**
	 * @param DO
	 * @return
	 */
	public int updateUser(UserDO DO);

	/**
	 * @param userId
	 * @return
	 */
	public int disableUser(Long userId);

}
