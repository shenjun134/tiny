/**
 * Configuration.java
 *
 *
 */
package com.tiny.common.configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author e521907
 * @version 1.0
 *
 */
public interface Configuration {
	/**
	 * @param key
	 * @return
	 */
	public String getValue(String key);

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key);

	/**
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key);

	/**
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key);

	/**
	 * @param key
	 * @return
	 */
	public Long getLong(String key);

	/**
	 * @param key
	 * @return
	 */
	public Float getFloat(String key);

	/**
	 * @param key
	 * @return
	 */
	public Double getDouble(String key);

	/**
	 * @param key
	 * @return
	 */
	public List<String> getStringList(String key);

	/**
	 * @param key
	 * @return
	 */
	public List<Integer> getIntegerList(String key);

	/**
	 * @param key
	 * @return
	 */
	public List<Long> getLongList(String key);

	/**
	 * @param key
	 * @return
	 */
	public List<Float> getFloatList(String key);

	/**
	 * @param key
	 * @return
	 */
	public List<Double> getDoubleList(String key);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Boolean getBoolean(String key, boolean defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Integer getInteger(String key, int defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Long getLong(String key, long defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Float getFloat(String key, float defaultValue);

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Double getDouble(String key, double defaultValue);

	/**
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * @return
	 */
	public Properties getProperties();

	/**
	 * @return
	 */
	public boolean reload();

	/**
	 * @return
	 */
	public String getDelimiter();

	/**
	 * @param delimiter
	 */
	public void setDelimiter(String delimiter);

	/**
	 * @return
	 */
	public boolean isSilentMissing();

	/**
	 * @param isSilentMissing
	 */
	public void setSilentMissing(boolean isSilentMissing);

}
