/**
 * PageMachine.java
 *
 * Oct 14, 2016 - 5:34:47 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.util;

import com.tiny.common.base.PageModel;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PageMachine {
	/**
	 * 
	 */
	private static final int	DEFAULT_PAGE_SIZE	= 15;

	/**
	 * @param page
	 * @param totalItems
	 */
	public static void enrichPageInfo(PageModel page, int totalItems) {
		if (page.getPageSize() <= 0) {
			page.setPageSize(DEFAULT_PAGE_SIZE);
		}
		int mo = totalItems % page.getPageSize();
		if (mo > 0) {
			page.setTotalPages(totalItems / page.getPageSize() + 1);
		} else {
			page.setTotalPages(totalItems / page.getPageSize());
		}
		if (page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		if (page.getCurrentPage() * page.getPageSize() > totalItems) {
			page.setCurrentPage(page.getTotalPages());
		}
		int beginPage = (page.getCurrentPage() - 1) * page.getPageSize();
		page.setBeginPage(beginPage);
		int endPage = page.getCurrentPage() * page.getPageSize() + 1;
		if (endPage > totalItems) {
			endPage = totalItems + 1;
		}
		page.setEndPage(endPage);
		page.setTotalItems(totalItems);
	}
}
