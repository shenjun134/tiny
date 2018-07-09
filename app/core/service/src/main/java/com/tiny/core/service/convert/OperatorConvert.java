/**
 * OperatorConvert.java
 *
 * Oct 26, 2016 - 3:47:25 PM
 *
 * "lemon-core-service
 *
 */
package com.tiny.core.service.convert;

import com.tiny.common.dal.dataobject.OperatorDO;
import com.tiny.core.model.OperatorModel;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class OperatorConvert {

	/**
	 * convert from Model to DO
	 * 
	 * @param model
	 * @return
	 */
	public static OperatorDO convetModel2DO(OperatorModel model) {
		if (model == null) {
			return null;
		}
		OperatorDO DO = new OperatorDO();
		DO.setComments(model.getComments());
		DO.setHost(model.getHost());
		DO.setId(model.getId());
		DO.setName(model.getName());
		return DO;
	}
}
