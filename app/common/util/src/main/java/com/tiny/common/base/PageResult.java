/**
 * PageResult.java
 * <p>
 * Oct 14, 2016 - 4:45:34 PM
 * <p>
 * "lemon-common-util
 */
package com.tiny.common.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PageResult<T> extends BaseResult {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6426812489012351916L;

    /**
     * page info
     */
    private PageModel page = new PageModel();

    /**
     * records
     */
    private List<T> records = new ArrayList<T>();

    /**
     * @param isSucc
     * @param message
     * @param records
     */
    public PageResult(boolean isSucc, String message, List<T> records) {
        this.isSucc = isSucc;
        this.message = message;
        this.records = records;
    }

    /**
     *
     */
    public PageResult() {
    }

    /**
     *
     */
    public PageResult(PageModel page) {
        this.page = page;
    }

    /**
     *
     */
    public PageResult(boolean isSucc) {
        super(isSucc);
    }

    /**
     * @param message
     * @param records
     */
    public void markeSuccess(String message, List<T> records) {
        this.isSucc = true;
        this.message = message;
        this.records = records;
    }

    /**
     * @param message
     */
    public PageResult markeSuccess(String message) {
        super.markeSuccess(message);
        return this;
    }

    /**
     * @param records
     */
    public PageResult markeSuccess(List<T> records) {
        this.isSucc = true;
        this.records = records;
        return this;
    }

    /**
     * @param message
     */
    public PageResult marketFail(String message) {
        super.marketFail(message);
        return this;
    }

    /**
     * @return the page
     */
    public PageModel getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(PageModel page) {
        this.page = page;
    }

    /**
     * @return the records
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }

}
