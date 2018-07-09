/**
 * Singleton4.java
 *
 * Sep 20, 2016 - 4:55:17 PM
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
public class Singleton4 {
	private static Singleton4 instance = null;
	
	static{
		instance = new Singleton4();
	}
	
	private Singleton4(){}
	
	
	public static Singleton4 getInstance(){
		return instance;
	}
}
