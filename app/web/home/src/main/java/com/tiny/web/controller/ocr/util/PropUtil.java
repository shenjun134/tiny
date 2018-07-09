package com.tiny.web.controller.ocr.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class PropUtil
{
    private final static Logger logger = Logger.getLogger(PropUtil.class);
    
    public static Map<String, Map<String, String>> propMap = new HashMap<String, Map<String, String>>(10);
  
    public static Map<String, String> load(String propKey) {

		if (propMap.get(propKey) == null || propMap.get(propKey).isEmpty()) {

			synchronized (propMap) {
				if (propMap.get(propKey) == null || propMap.get(propKey).isEmpty()) {
					Map<String, String> map = loadProperty(propKey);
					propMap.put(propKey, map);
				}
			}
		}

		return propMap.get(propKey);
	}

	public static Map<String, String> loadProperty(String fileName) {

		Map<String, String> map;
		try {
			Properties p = new Properties();
			p.load(PropUtil.class.getResourceAsStream("/config/" + fileName + ".properties"));

			map = new HashMap<String, String>(20);
			for (Entry entry : p.entrySet()) {
				map.put((String) entry.getKey(), (String) entry.getValue());
			}
		} catch (IOException e) {
			logger.error("Exception in loadProperty", e);
			return null;
		}

		return map;
	}

}
