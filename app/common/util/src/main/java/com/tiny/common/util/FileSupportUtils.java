/**
 * FileSupportUtils.java
 *
 *
 */
package com.tiny.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class FileSupportUtils {
	/**
	 * the the file content by path
	 * 
	 * @param path
	 * @return
	 */
	public static StringBuffer getString(String path) {
		StringBuffer xml = new StringBuffer();
		InputStreamReader streamReader;
		BufferedReader br = null;
		try {
			streamReader = new InputStreamReader(new FileInputStream(new File(path)));
			br = new BufferedReader(streamReader);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				xml.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xml;
	}

	/**
	 * @param file
	 * @return
	 */
	public static StringBuffer getString(File file) {
		StringBuffer xml = new StringBuffer();
		InputStreamReader streamReader;
		BufferedReader br = null;
		try {
			streamReader = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(streamReader);
			String temp = null;
			while ((temp = br.readLine()) != null) {
				xml.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return xml;
	}
}
