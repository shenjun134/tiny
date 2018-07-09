/**
 *
 */
package com.tiny.web.controller;

import com.tiny.common.base.ToString;

/**
 * @author e521907
 */
public class BaseJsonResult extends ToString {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2556012854452338622L;

    /**
     *
     */
    private String message;

    /**
     *
     */
    private boolean status;

    /**
     *
     */
    private Object data;


    /**
     * @param message
     * @param status
     */
    public BaseJsonResult(String message, boolean status) {
        this(message, status, null);
    }

    /**
     *
     */
    public BaseJsonResult() {
        this(null, false, null);
    }

    /**
     * @param message
     * @param status
     * @param data
     */
    public BaseJsonResult(String message, boolean status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    /**
     * @param message
     * @param data
     */
    public BaseJsonResult markeSuccess(String message, Object data) {
        this.message = message;
        this.data = data;
        this.status = true;
        return this;
    }

    /**
     * @param message
     */
    public BaseJsonResult marketFail(String message) {
        this.message = message;
        this.status = false;
        this.data = null;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }
}
