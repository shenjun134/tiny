/**
 * ResultCopy.java
 *
 * Jun 16, 2017 - 3:35:33 PM
 *
 * "tiny-core-service
 *
 */
package com.tiny.core.service.copy;

import com.tiny.common.base.CommonResult;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class ResultCopy {

	/**
	 * copy result1 to result2
	 * 
	 * @param result1
	 * @param result2
	 */
	public static <T> void copy(CommonResult<T> result1, CommonResult<T> result2) {
		result2.setData(result1.getData());
		result2.setMessage(result1.getMessage());
		result2.setSucc(result1.isSucc());
	}

}
