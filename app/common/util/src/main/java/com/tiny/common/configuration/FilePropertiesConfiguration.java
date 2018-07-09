/**
 * FilePropertiesConfiguration.java
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
public class FilePropertiesConfiguration extends PropertiesConfiguration {

	/**
	 * @param filename
	 */
	public FilePropertiesConfiguration(String filename) {
		this.filename = filename;
		reload();
	}

	/**
	 * @param url
	 */
	public FilePropertiesConfiguration(URL url) {
		this.url = url;
		reload();
	}

	/**
	 * @param inputStream
	 */
	public FilePropertiesConfiguration(InputStream inputStream) {
		properties = CommonUtil.retrieveFileProperties(inputStream);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean reload() {
		propertiesLock.writeLock().lock();
		try {
			if (filename != null) {
				inputStream = CommonUtil.getInputStream(filename);
			} else if (url != null) {
				inputStream = CommonUtil.getInputStream(url);
			}
			if (inputStream != null) {
				properties = CommonUtil.retrieveFileProperties(inputStream);
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
