/**
 * Singleton2.java
 *
 * Sep 20, 2016 - 4:52:50 PM
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
public class Singleton2 {
	private static Singleton2 instance;
	
	private Singleton2(){}
	
	public static synchronized Singleton2 getInstance(){
		if(instance == null){
			instance = new Singleton2();
		}
		return instance;
	}
	
}
