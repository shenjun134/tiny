/**
 * PageModel.java
 *
 * Oct 14, 2016 - 4:41:30 PM
 *
 * "lemon-core-model
 *
 */
package com.tiny.common.base;


/**
 * @author e521907
 * @version 1.0
 *
 */
public class PageModel extends ToString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2099651152916175106L;
	/**
	 * begin ROWNUM index
	 */
	private int					beginPage;

	/**
	 * end ROWNUM index
	 */
	private int					endPage;

	/**
	 * current Page index
	 */
	private int					currentPage;

	/**
	 * total Page count
	 */
	private int					totalPages;

	/**
	 * total Records count
	 */
	private int					totalItems;

	/**
	 * default size 15
	 */
	private int					pageSize			= 15;

	/**
	 * @return the beginPage
	 */
	public int getBeginPage() {
		return beginPage;
	}

	/**
	 * @param beginPage the beginPage to set
	 */
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @param endPage the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalItems
	 */
	public int getTotalItems() {
		return totalItems;
	}

	/**
	 * @param totalItems the totalItems to set
	 */
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
