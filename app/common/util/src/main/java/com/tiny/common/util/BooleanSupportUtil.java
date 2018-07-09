/**
 * BooleanSupportUtil.java
 *
 * Oct 24, 2016 - 3:49:02 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class BooleanSupportUtil {

	/**
	 * BooleanSupportUtil.convertActiveFlg
	 * 
	 * <pre>
	 * 		y|Y == true
	 * 		else == false
	 * </pre>
	 * 
	 * 
	 * @param activeFlg
	 * @return
	 */
	public static boolean convertActiveFlg(String activeFlg) {
		return StringUtils.equalsIgnoreCase("Y", activeFlg);
	}

}
