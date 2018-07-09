package com.tiny.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class StringSupportUtils {

	/** ���ַ� */
	public static final String	EMPTY_STRING	= "";

	/** �ո��ַ� */
	public static final String	BLANK_STRING	= " ";

	/**
	 * ����ַ��Ƿ�Ϊ<code>null</code>����ַ�<code>""</code>��
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty(&quot;&quot;)        = true
	 * StringUtil.isEmpty(&quot; &quot;)       = false
	 * StringUtil.isEmpty(&quot;bob&quot;)     = false
	 * StringUtil.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ�
	 * 
	 * @return ���Ϊ��, �򷵻�<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * ����ַ��Ƿ��ǿհף�<code>null</code>�����ַ�<code>""</code>��ֻ�пհ��ַ�
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank(&quot;&quot;)        = true
	 * StringUtil.isBlank(&quot; &quot;)       = true
	 * StringUtil.isBlank(&quot;bob&quot;)     = false
	 * StringUtil.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ�
	 * 
	 * @return ���Ϊ�հ�, �򷵻�<code>true</code>
	 */
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ����ַ���<code>null</code>����ַ�<code>""</code>���򷵻ؿ��ַ�<code>""</code>
	 * �����򷵻��ַ��?
	 * 
	 * <p>
	 * �˷���ʵ���Ϻ�<code>defaultIfNull(String)</code>��Ч��
	 * 
	 * <pre>
	 * StringUtil.defaultIfEmpty(null)  = &quot;&quot;
	 * StringUtil.defaultIfEmpty(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.defaultIfEmpty(&quot;  &quot;)  = &quot;  &quot;
	 * StringUtil.defaultIfEmpty(&quot;bat&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ�
	 * 
	 * @return �ַ������ַ�<code>""</code>
	 */
	public static String defaultIfEmpty(String str) {
		return (str == null) ? EMPTY_STRING : str;
	}

	/**
	 * �Ƚ������ַ���Сд�����У���
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, &quot;abc&quot;)  = false
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, null)  = false
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
	 * </pre>
	 * 
	 * @param str1
	 *            Ҫ�Ƚϵ��ַ�1
	 * @param str2
	 *            Ҫ�Ƚϵ��ַ�2
	 * 
	 * @return ��������ַ���ͬ�����߶���<code>null</code>���򷵻�<code>true</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * ȡָ���ַ���Ӵ���
	 * 
	 * <p>
	 * �����������β����ʼ���㡣����ַ�Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring(&quot;&quot;, * ,  *)    = &quot;&quot;;
	 * StringUtil.substring(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 0)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 * StringUtil.substring(&quot;abc&quot;, 4, 6)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 2)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, -2, -1) = &quot;b&quot;
	 * StringUtil.substring(&quot;abc&quot;, -4, 2)  = &quot;ab&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ�
	 * @param start
	 *            ��ʼ�������Ϊ�����ʾ��β������
	 * @param end
	 *            ����������������Ϊ�����ʾ��β������
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY_STRING;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	public static Map<String, String> exprSplit(String content) {
		Map<String, String> map = new HashMap<String, String>();
		int sidx = 0;
		int eqidx = content.indexOf(')', sidx);
		while (eqidx != -1) {
			int douidx = content.indexOf(',', eqidx);
			if (douidx == -1) {
				map.put(content.substring(sidx, eqidx + 1), content.substring(eqidx + 2));
				break;
			} else {
				map.put(content.substring(sidx, eqidx + 1), content.substring(eqidx + 2, douidx));
				sidx = douidx + 1;
				eqidx = content.indexOf(')', sidx);
			}
		}
		return map;
	}

	public static String concat(List<Object> list, char tag) {
		StringBuilder str = new StringBuilder("");
		for (Object obj : list) {
			str.append(obj.toString()).append(tag);
		}
		return str.toString();
	}

	public static String list2String(List<Object[]> source) {
		String result = "";
		if (source != null && source.size() > 0) {
			String[] objArr = new String[source.size()];
			for (int i = 0; i < source.size(); i++) {
				Object[] tmpArr = source.get(i);
				for (int j = 0; j < tmpArr.length; j++) {
					tmpArr[j] = tmpArr[j] == null ? "\"\"" : "\"" + tmpArr[j] + "\"";
				}
				objArr[i] = Arrays.toString(tmpArr);
			}

			result = "{\"aaData\":" + Arrays.toString(objArr) + "}";
		} else {
			result = "{\"aaData\":" + "\"\"" + "}";
		}

		return result;
	}

	public static String stringToJson(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b': // �˸�
					sb.append("\\b");
					break;
				case '\f': // ��ֽ��ҳ
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n"); // ����
					break;
				case '\r': // �س�
					sb.append("\\r");
					break;
				case '\t': // �������
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	public static final String	FILL_TYPE_PREFIX	= "prefix";

	public static final String	FILL_TYPE_SUFFIX	= "suffix";

	/**
	 * �ж�value�����Ƿ�Ϊlength������ǣ�����ǰ�����filler��ֱ������Ϊlength
	 * 
	 * @param value
	 * @param filler
	 * @param length
	 * @return
	 */
	public static String fillPrefix(String value, String filler, int length) {
		return fill(value, filler, FILL_TYPE_PREFIX, length);
	}

	/**
	 * �ж�value�����Ƿ�Ϊlength������ǣ����ں������filler��ֱ������Ϊlength
	 * 
	 * @param value
	 * @param filler
	 * @param length
	 * @return
	 */
	public static String fillSuffix(String value, String filler, int length) {
		return fill(value, filler, FILL_TYPE_SUFFIX, length);
	}

	private static String fill(String value, String filler, String fillType, int length) {
		StringBuffer buf = new StringBuffer(defaultIfEmpty(value));
		while (buf.length() < length) {
			if (fillType.equals(FILL_TYPE_PREFIX)) {
				buf.insert(0, filler);
			} else if (fillType.equals(FILL_TYPE_SUFFIX)) {
				buf.append(filler);
			} else {
				throw new RuntimeException("�޷�ʶ���fillType��" + fillType);
			}
		}
		return buf.toString();
	}

	/**
	 * @param obj
	 * @return
	 */
	public static String getObject2Str(Object obj) {
		if (null == obj) {
			return StringUtils.EMPTY;
		}
		return obj.toString();
	}

}
