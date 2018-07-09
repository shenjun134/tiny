/**
 * Singleton1.java
 *
 * Sep 20, 2016 - 4:50:58 PM
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
public class Singleton1 {
	private static Singleton1 instance;
	
	private Singleton1(){}
	
	public static Singleton1 getInstance(){
		if(instance == null){
			instance = new Singleton1();
		}
		return instance;
	}
}
