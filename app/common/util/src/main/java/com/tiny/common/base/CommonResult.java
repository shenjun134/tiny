/**
 * CommonResult.java
 */
package com.tiny.common.base;

/**
 * @author e521907
 * @version 1.0
 */
public class CommonResult<T> extends BaseResult {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6669352878051004190L;

    /**
     * data
     */
    protected T data;

    /**
     * @param isSucc
     * @param message
     * @param data
     */
    public CommonResult(boolean isSucc, String message, T data) {
        super(isSucc, message);
        this.data = data;
    }

    /**
     * @return
     */
    public static CommonResult newSucc() {
        return new CommonResult(true);
    }


    /**
     * @param messagem
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> newSucc(String messagem, T data) {
        return new CommonResult(true, messagem, data);
    }

    /**
     * @return
     */
    public static CommonResult newFail() {
        return new CommonResult(false);
    }

    /**
     * @param message
     * @return
     */
    public static CommonResult newFail(String message) {
        return new CommonResult(false, message, null);
    }

    /**
     *
     */
    public CommonResult() {
    }

    /**
     *
     */
    public CommonResult(boolean isSucc) {
        super(isSucc);
    }

    /**
     * @param message
     * @param data
     */
    public CommonResult markeSuccess(String message, T data) {
        this.message = message;
        this.isSucc = true;
        this.data = data;
        return this;
    }

    /**
     * @param message
     */
    public CommonResult markeSuccess(String message) {
        this.message = message;
        this.isSucc = true;
        return this;
    }

    /**
     * @param data
     */
    public CommonResult markeSuccess(T data) {
        this.data = data;
        this.isSucc = true;
        return this;
    }

    /**
     * @param message
     */
    public CommonResult marketFail(String message) {
        this.message = message;
        this.isSucc = false;
        return this;
    }

    /**
     * @return the isSucc
     */
    public boolean isSucc() {
        return isSucc;
    }

    /**
     * @param isSucc the isSucc to set
     */
    public void setSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

}
