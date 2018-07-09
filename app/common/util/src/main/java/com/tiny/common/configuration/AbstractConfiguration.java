/**
 * AbstractConfiguration.java
 *
 *
 */
package com.tiny.common.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tiny.common.exception.ConfigurationException;
import com.tiny.common.util.CommonUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public abstract class AbstractConfiguration implements Configuration {
	private static final Logger	LOGGER			= Logger.getLogger(AbstractConfiguration.class);

	private String				delimiter		= ",";

	private boolean				isSilentMissing	= true;

	public String getString(String key) {
		String value = getValue(key);
		if (value == null && !isSilentMissing) {
			throw new ConfigurationException("Missing property for " + key);
		}

		return value;
	}

	public String getString(String key, String defaultValue) {
		String value = getValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public Boolean getBoolean(String key) {
		String value = getString(key);
		if (value != null) {
			try {
				return Boolean.parseBoolean(value);
			} catch (NumberFormatException e) {
				String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
				LOGGER.warn(error, e);
				if (!isSilentMissing) {
					throw new ConfigurationException(error, e);
				}
			}
		}

		return null;
	}

	public Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		if (value != null) {
			try {
				return Boolean.parseBoolean(value);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid value " + value + " for key " + key + " with error " + e.getMessage(), e);
			}
		}

		return defaultValue;
	}

	public Integer getInteger(String key) {
		String value = getString(key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
				LOGGER.warn(error, e);
				if (!isSilentMissing) {
					throw new ConfigurationException(error, e);
				}
			}
		}

		return null;
	}

	public Integer getInteger(String key, int defaultValue) {
		String value = getValue(key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid value " + value + " for key " + key + " with error " + e.getMessage(), e);
			}
		}

		return defaultValue;
	}

	public Long getLong(String key) {
		String value = getString(key);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
				LOGGER.warn(error, e);
				if (!isSilentMissing) {
					throw new ConfigurationException(error, e);
				}
			}
		}

		return null;
	}

	public Long getLong(String key, long defaultValue) {
		String value = getValue(key);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid value " + value + " for key " + key + " with error " + e.getMessage(), e);
			}
		}

		return defaultValue;
	}

	public Float getFloat(String key) {
		String value = getString(key);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException e) {
				String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
				LOGGER.warn(error, e);
				if (!isSilentMissing) {
					throw new ConfigurationException(error, e);
				}
			}
		}

		return null;
	}

	public Float getFloat(String key, float defaultValue) {
		String value = getValue(key);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid value " + value + " for key " + key + " with error " + e.getMessage(), e);
			}
		}

		return defaultValue;
	}

	public Double getDouble(String key) {
		String value = getString(key);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
				LOGGER.warn(error, e);
				if (!isSilentMissing) {
					throw new ConfigurationException(error, e);
				}
			}
		}

		return null;
	}

	public Double getDouble(String key, double defaultValue) {
		String value = getValue(key);
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				LOGGER.warn("Invalid value " + value + " for key " + key + " with error " + e.getMessage(), e);
			}
		}

		return defaultValue;
	}

	public List<String> getStringList(String key) {
		String value = getString(key);
		if (value != null) {
			return CommonUtil.splitString(value, delimiter);
		}

		return new ArrayList<String>();
	}

	public List<Integer> getIntegerList(String key) {
		List<String> arrays = getStringList(key);
		if (arrays != null) {
			List<Integer> integerList = new ArrayList<Integer>(arrays.size());
			for (String value : arrays) {
				try {
					integerList.add(Integer.parseInt(value));
				} catch (NumberFormatException e) {
					String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
					LOGGER.warn(error, e);
					throw new ConfigurationException(error, e);
				}
			}

			return integerList;
		}

		return null;
	}

	public List<Long> getLongList(String key) {
		List<String> arrays = getStringList(key);
		if (arrays != null) {
			List<Long> longList = new ArrayList<Long>(arrays.size());
			for (String value : arrays) {
				try {
					longList.add(Long.parseLong(value));
				} catch (NumberFormatException e) {
					String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
					LOGGER.warn(error, e);
					throw new ConfigurationException(error, e);
				}
			}

			return longList;
		}

		return null;
	}

	public List<Float> getFloatList(String key) {
		List<String> arrays = getStringList(key);
		if (arrays != null) {
			List<Float> floatList = new ArrayList<Float>(arrays.size());
			for (String value : arrays) {
				try {
					floatList.add(Float.parseFloat(value));
				} catch (NumberFormatException e) {
					String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
					LOGGER.warn(error, e);
					throw new ConfigurationException(error, e);
				}
			}

			return floatList;
		}

		return null;
	}

	public List<Double> getDoubleList(String key) {
		List<String> arrays = getStringList(key);
		if (arrays != null) {
			List<Double> doubleList = new ArrayList<Double>(arrays.size());
			for (String value : arrays) {
				try {
					doubleList.add(Double.parseDouble(value));
				} catch (NumberFormatException e) {
					String error = "Invalid value " + value + " for key " + key + " with error " + e.getMessage();
					LOGGER.warn(error, e);
					throw new ConfigurationException(error, e);
				}
			}

			return doubleList;
		}

		return null;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean isSilentMissing() {
		return isSilentMissing;
	}

	public void setSilentMissing(boolean isSilentMissing) {
		this.isSilentMissing = isSilentMissing;
	}

}
