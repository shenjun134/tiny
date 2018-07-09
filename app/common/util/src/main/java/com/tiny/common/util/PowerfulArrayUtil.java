package com.tiny.common.util;

import java.util.ArrayList;
import java.util.List;

public class PowerfulArrayUtil {

	public static boolean isEmpty(Object[] arrs) {
		return null == arrs || arrs.length == 0;
	}

	public static <T> List<T> covert2List(T[] arrs) {
		if (null == arrs) {
			return null;
		}
		List<T> tList = new ArrayList<T>();
		for (int i = 0; i < arrs.length; i++) {
			tList.add(arrs[i]);
		}
		return tList;
	}
	
	

}
