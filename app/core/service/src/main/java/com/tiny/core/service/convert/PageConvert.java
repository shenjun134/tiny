/**
 * PageConvert.java
 *
 * Oct 14, 2016 - 5:03:33 PM
 *
 * "lemon-core-service
 *
 */
package com.tiny.core.service.convert;

import com.tiny.common.base.PageModel;
import com.tiny.common.dal.dataobject.PageDO;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PageConvert {

	/**
	 * @param model
	 * @return
	 */
	public static PageDO convertModel2DO(PageModel model) {
		if (model == null) {
			return null;
		}
		PageDO DO = new PageDO();
		DO.setBeginIndex(model.getBeginPage());
		DO.setCurrentPageIndex(model.getCurrentPage());
		DO.setEndIndex(model.getEndPage());
		DO.setTotalPageCount(model.getTotalPages());
		DO.setTotalRecordsCount(model.getTotalItems());
		return DO;
	}

}
