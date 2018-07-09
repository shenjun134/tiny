/**
 * UrlLevel.java
 *
 * Oct 24, 2016 - 2:53:17 PM
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
public enum UrlLevel {
	NAV(0),

	FIRST_MENU(1),

	SECOND_MENU(2), ;

	private int	level;

	private UrlLevel() {

	}

	/**
	 * get UrlLevel which level is the same 
	 * 
	 * @param level
	 * @return
	 */
	public static UrlLevel getUrlLevel(int level) {
		for (UrlLevel temp : values()) {
			if (temp.level - level == 0) {
				return temp;
			}
		}
		return null;
	}

	private UrlLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
