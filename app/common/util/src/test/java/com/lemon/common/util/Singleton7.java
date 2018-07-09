/**
 * Singleton7.java
 *
 * Sep 20, 2016 - 5:00:42 PM
 *
 * "lemon-common-util
 *
 */
package com.lemon.common.util;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class Singleton7 {
	public volatile static Singleton7	instance;

	private Singleton7() {
	}

	public static Singleton7 getInstance() {
		if (instance == null) {
			synchronized (instance) {
				if (instance == null) {
					instance = new Singleton7();
				}
			}
		}
		return instance;
	}

}
