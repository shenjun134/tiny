/**
 * IbatisCommonDao.java
 *
 * Oct 12, 2016 - 4:42:41 PM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.daointerface;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface IbatisCommonDao {

	String	NAMESPACE	= "common.";

	/**
	 * @return
	 */
	public Long queryUserSequence();
}
