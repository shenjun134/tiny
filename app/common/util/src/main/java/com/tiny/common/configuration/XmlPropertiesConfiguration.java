/**
 * XmlPropertiesConfiguration.java
 *
 *
 */
package com.tiny.common.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.tiny.common.util.CommonUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class XmlPropertiesConfiguration extends PropertiesConfiguration {

	/**
	 * @param filename
	 */
	public XmlPropertiesConfiguration(String filename) {
		this.filename = filename;
		reload();
	}

	/**
	 * @param url
	 */
	public XmlPropertiesConfiguration(URL url) {
		this.url = url;
		reload();
	}

	/**
	 * @param inputStream
	 */
	public XmlPropertiesConfiguration(InputStream inputStream) {
		properties = CommonUtil.retrieveXmlProperties(inputStream);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean reload() {
		InputStream inputStream = null;
		propertiesLock.writeLock().lock();
		try {
			if (filename != null) {
				inputStream = CommonUtil.getInputStream(filename);
			} else if (url != null) {
				inputStream = CommonUtil.getInputStream(url);
			}
			if (inputStream != null) {
				properties = CommonUtil.retrieveXmlProperties(inputStream);
				return true;
			}
		} finally {
			propertiesLock.writeLock().unlock();
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}

		return false;
	}

}
