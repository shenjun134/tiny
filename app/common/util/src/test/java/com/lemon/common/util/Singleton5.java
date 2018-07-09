/**
 * Singleton5.java
 *
 * Sep 20, 2016 - 4:56:37 PM
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
public class Singleton5 {

	private Singleton5() {
	}

	private static class Singleton5Holder {
		private static Singleton5	INSTANCE	= new Singleton5();
	}

	public static final Singleton5 getInstance() {
		return Singleton5Holder.INSTANCE;
	}
}
