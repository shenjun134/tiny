/**
 *
 */
package com.tiny.common.base;

/**
 * @author e521907
 */
public class BaseResult extends ToString {

    /**
     *
     */
    private static final long serialVersionUID = 5257743624405070550L;

    /**
     * market succ
     */
    protected boolean isSucc = false;

    /**
     * message
     */
    protected String message;

    /**
     * @param isSucc
     * @param message
     */
    public BaseResult(boolean isSucc, String message) {
        this.isSucc = isSucc;
        this.message = message;
    }

    /**
     *
     */
    public BaseResult() {
    }


    /**
     *
     */
    public BaseResult(boolean isSucc) {
        this.isSucc = isSucc;
    }

    /**
     * @param message
     */
    public BaseResult markeSuccess(String message) {
        this.message = message;
        this.isSucc = true;
        return this;
    }

    /**
     * @param message
     */
    public BaseResult marketFail(String message) {
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

}
