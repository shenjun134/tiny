/**
 * NameValueConvert.java
 *
 * Dec 6, 2016 - 2:57:47 PM
 *
 * "lemon-core-service
 *
 */
package com.tiny.core.service.convert;

import com.tiny.common.dal.dataobject.NameValueDO;
import com.tiny.core.model.NameValueModel;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class NameValueConvert {
	/**
	 * @param DO
	 * @return
	 */
	public static NameValueModel convertDO2Model(NameValueDO DO) {
		if (DO == null) {
			return null;
		}
		return new NameValueModel(DO.getName(), DO.getValue());

	}
}
