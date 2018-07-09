package com.tiny.web.controller.ocr.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.opencv_core.Rect;

import com.tiny.web.controller.ocr.model.Field;

public class StrUtil
{

	private final static Logger logger = Logger.getLogger(StrUtil.class);

	public static String mapToString(Map<String, Field> fieldMap)
	{
		StringBuilder sb = new StringBuilder();

		for (Entry<String, Field> entry : fieldMap.entrySet())
		{
			sb.append("\r\n").append(entry.getKey() + "=" + entry.getValue().getValue());
		}

		return sb.toString();
	}

	public static boolean isNullOrEmtry(String s)
	{
		return (s == null || s.trim().length() == 0) ? true : false;
	}

	public static Field getField(String line)
	{
		if (StrUtil.isNullOrEmtry(line))
			return null;

		int index = StringUtils.indexOfAny(line, new char[]{':', ';'});
		String label = "";
		String value = "";
		if (index > -1)
		{
			label = line.substring(0, index);
			value = line.substring(index + 1, line.length());
		}
		else
		{
			value = line;
		}
		return new Field(label.trim(), value.trim());
	}

//	public static Field splitField(String line, Rect r)
//	{
//		Field field = splitField(line);
//		if (field != null)
//			field.setLocation(r.x(), r.y(), r.width(), r.height());
//		return field;
//	}

	public static Field splitField(String line)
	{
		if (StrUtil.isNullOrEmtry(line))
			return null;

		int index = line.indexOf(':');
		String label = "";
		String value = "";
		if (index > -1)
		{
			label = line.substring(0, index);
			value = line.substring(index + 1, line.length());
		}
		else
		{
			index = StringUtils.indexOfAny(line, new String[]{".‘", ".'"});
			if (index > -1)
			{
				label = line.substring(0, index);
				value = line.substring(index + 2, line.length());
			}
			else
			{
				label = line;
				value = line;
			}
		}

		return new Field(label.trim(), value.trim());
	}
}
