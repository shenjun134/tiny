/**
 * UserLogEnum.java
 *
 * Oct 12, 2016 - 2:01:10 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.enums;

/**
 * @author e521907
 * @version 1.0
 *
 */
public enum UserLogEnum implements EnumBase {
	LOGIN(1),

	LOGOUT(0)

	;

	private int	inout;

	private UserLogEnum(int inout) {
		this.inout = inout;
	}

	/**
	 * @return the inout
	 */
	public int getInout() {
		return inout;
	}

	/**
	 * @param inout the inout to set
	 */
	public void setInout(int inout) {
		this.inout = inout;
	}
	
	public static UserLogEnum inoutOf(int inout){
		if(inout <= 0){
			return LOGOUT;
		}
		return LOGIN;
	}

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

}
