package com.tiny.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * ����
 * 
 * @author dumao.qiu
 * @version $Id: SupportUtils.java, v 0.1 2014��5��9�� ����11:53:48 QDM Exp $
 */
public class SupportUtils {

	@SuppressWarnings("unused")
	private static final Logger	logger		= Logger.getLogger(SupportUtils.class);

	public final static Pattern	ipPattern	= Pattern
													.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

	public static boolean checkIp(String ip) {
		return ipPattern.matcher(ip).matches();
	}

	public static String getIpAddr(HttpServletRequest request) {
		try {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �����ַ���ת��ΪList����
	 * 
	 * @param value
	 * @param regex �ָ���
	 * @return
	 */
	public static List<String> toList(String value, String regex) {
		List<String> data = new ArrayList<String>();
		String[] value_1D = value.split(regex);
		for (String temp : value_1D) {
			if (StringUtils.isEmpty(temp)) {
				continue;
			}
			data.add(temp.trim());
		}
		return data;
	}

	/**
	 * ��ȡ������д��Сд�����֡�_
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String str = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuvwxyz0123456789_";
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(random.nextInt(str.length())));
		}
		return sb.toString();
	}

	/**
	 * ��ȡ�����
	 * 
	 * @param letter �ִ����紿���֡�abcdef
	 * @param length
	 * @return
	 */
	public static String getRandomByLetter(String letter, int length) {
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(letter.charAt(random.nextInt(letter.length())));
		}
		return sb.toString();
	}

	/**
	 * ������
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyObject(T t) {
		T obj = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(t);
			out.flush();
			out.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bis);
			obj = (T) in.readObject();
		} catch (Exception e) {
		}
		return obj;
	}

	/**
	 * �����쳣
	 * 
	 * @param t
	 * @param length
	 * @return
	 */
	public static String formatterException(Throwable t, Integer length) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		String content = sw.toString();
		if (length == null) {
			return content;
		}
		try {
			return content.substring(0, length);

		} catch (Exception e) {
			return content;
		}

	}

	/**
	 * Ӣ�ı����ת����
	 * 
	 * @param value
	 * @return
	 */
	public static String toChinesePunctuation(String value) {
		String temp = value.replaceAll(",", "��");
		return temp.trim();
	}

	/**
	 * Unicode����
	 * 
	 * @param unicodeStr
	 * @return
	 */
	public static String decodeUnicode(String unicodeStr) {
		char aChar;
		int len = unicodeStr.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = unicodeStr.charAt(x++);
			if (aChar == '\\') {
				aChar = unicodeStr.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = unicodeStr.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static String getWebHome() {
		return System.getProperty("center.root");

	}

	public static String getFilePath(String path) {
		return System.getProperty("center.root") + path;
	}

}
