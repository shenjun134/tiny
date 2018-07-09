/**
 * PageDO.java
 *
 * Oct 14, 2016 - 4:27:06 PM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.dataobject;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PageDO extends BaseDO {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2152593102049617795L;

	/**
	 * begin ROWNUM index
	 */
	private int					beginIndex;

	/**
	 * end ROWNUM index
	 */
	private int					endIndex;

	/**
	 * current Page index
	 */
	private int					currentPageIndex;

	/**
	 * total	Page count
	 */
	private int					totalPageCount;

	/**
	 * total Records count
	 */
	private int					totalRecordsCount;

	/**
	 * @return the beginIndex
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex the beginIndex to set
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the currentPageIndex
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	/**
	 * @param currentPageIndex the currentPageIndex to set
	 */
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	/**
	 * @return the totalPageCount
	 */
	public int getTotalPageCount() {
		return totalPageCount;
	}

	/**
	 * @param totalPageCount the totalPageCount to set
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 * @return the totalRecordsCount
	 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}

	/**
	 * @param totalRecordsCount the totalRecordsCount to set
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}

}
