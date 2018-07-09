/**
 * AbstractTransactionCheckCallback.java
 *
 *
 */
package com.tiny.common.transaction;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface ServiceCheckCallback {

	/**
	* @return
	*/
	void doTransaction();

	/**
	*
	*/
	void check();

}
