/**
 * Function2Test.java
 *
 * Dec 7, 2016 - 11:28:39 AM
 *
 * "lemon-common-util 
 *
 */
package com.lemon.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class Function2Test {
	public static void main(String[] args) {
		System.out.println(1/2);
		System.out.println(1.0/2.0);
		
		System.out.println(Double.valueOf(1)/2);
		
		System.out.println(StringUtils.substring("GCE_META.ssssssssss", "GCE_META.".length()));
		
		
		
		Date dt = new Date(1488690000000L);
		
		Calendar c = Calendar.getInstance();
		//Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.setTime(dt);
		
		System.out.println(dt);
		
		System.out.println(c);
	}
}
