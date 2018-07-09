/**
 * CommonUtil.java
 *
 *
 */
package com.tiny.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tiny.common.enums.IntervalUnit;
import com.tiny.common.exception.InitializationException;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class CommonUtil {
	private static final Logger		logger					= Logger.getLogger(CommonUtil.class);

	private static final long		NANO_TO_MILLIS			= 1000000L;

	private static final long		MILLIS_TO_SECONDS		= 1000L;

	private static final String		DEFAULT_LOCAL_HOSTNAME	= "localhost";

	private static final String		DEFAULT_LOCAL_IPADDRESS	= "127.0.0.1";

	private static final String		DEFAULT_PID				= "0";

	private static final Pattern	DASH_PATTERN			= Pattern.compile("-");

	private CommonUtil() {
	}

	/**
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	public static long getMilliseconds(long time, IntervalUnit timeUnit) {
		long millis = 0;

		switch (timeUnit) {
			case HOUR:
				millis = time * 3600000;
				break;
			case MINUTE:
				millis = time * 60000;
				break;
			case SECOND:
				millis = time * 1000;
				break;
			default:
				millis = time;
		}

		return millis;
	}

	/**
	 * @param nanoTime
	 * @return
	 */
	public static long nanoToMillis(long nanoTime) {
		return nanoTime / NANO_TO_MILLIS;
	}

	/**
	 * @param millisTime
	 * @return
	 */
	public static long millisToSeconds(long millisTime) {
		return millisTime / MILLIS_TO_SECONDS;
	}

	/**
	 * @param text
	 * @param delimiter
	 * @return
	 */
	public static List<String> splitString(String text, String delimiter) {
		List<String> list = new ArrayList<String>();
		int from = 0;
		int index;
		while ((index = text.indexOf(delimiter, from)) >= 0) {
			list.add(text.substring(from, index));
			from = ++index;
		}
		list.add(text.substring(from).trim());

		return list;
	}

	/**
	 * @param target
	 * @param source
	 */
	public static void mergeProperties(Properties target, Properties source) {
		Set<Entry<Object, Object>> entrySet = source.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			target.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @param properties
	 * @param basePath
	 *            Prefix to identify required properties, which should include
	 *            '.' in general.
	 * @return
	 */
	public static Properties getSubProperties(Properties properties, String basePath) {
		Properties subset = new Properties();
		int length = basePath.length();

		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = entry.getKey().toString();
			if (key.startsWith(basePath)) {
				key = key.substring(length);
				if (key.length() > 0) {
					subset.put(key, entry.getValue());
				}
			}
		}

		return subset;
	}

	/**
	 * @param resourceName
	 * @return
	 */
	public static boolean isResourceExist(String resourceName) {
		InputStream inputStream = null;
		try {
			inputStream = getClassLoader().getResourceAsStream(resourceName);
			return (inputStream == null) ? false : true;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * @param inputStream
	 * @return
	 */
	public static Properties retrieveFileProperties(InputStream inputStream) {
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (InvalidPropertiesFormatException e) {
			String error = "Invalid properties format with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		} catch (IOException e) {
			String error = "Unable to load properties with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		}

		return properties;
	}

	/**
	 * @param inputStream
	 * @return
	 */
	public static Properties retrieveXmlProperties(InputStream inputStream) {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(inputStream);
		} catch (InvalidPropertiesFormatException e) {
			String error = "Invalid XML properties format with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		} catch (IOException e) {
			String error = "Unable to load XML properties with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		}

		return properties;
	}

	/**
	 * @return
	 */
	public static Properties retrieveSystemProperties() {
		return System.getProperties();
	}

	/**
	 * @param inputStream
	 * @return
	 */
	public static Document retrieveXmlDocument(InputStream inputStream) {
		Document document = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(inputStream);
		} catch (Exception e) {
			String error = "Unable to parse XML configuration with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		}

		return document;
	}

	/**
	 * @param node
	 * @return
	 */
	public static String getDocumentPath(Node node) {
		StringBuilder builder = new StringBuilder(node.getNodeName());

		if (node instanceof Attr) {
			node = ((Attr) node).getOwnerElement();
			builder.insert(0, node.getNodeName() + ".");
		}
		while (node.getParentNode() != null) {
			node = node.getParentNode();
			if (node instanceof Document) {
				break;
			}
			builder.insert(0, node.getNodeName() + ".");
		}

		return builder.toString();
	}

	/**
	 * @param filename
	 * @return
	 */
	public static InputStream getInputStream(String filename) {
		InputStream inputStream = getClassLoader().getResourceAsStream(filename);
		if (inputStream == null) {
			String error = "Unable to locate file " + filename;
			logger.error(error);
			throw new InitializationException(error);
		}

		return inputStream;
	}

	/**
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader loader = CommonUtil.class.getClassLoader();
		if (loader == null) {
			loader = ClassLoader.getSystemClassLoader();
		}

		return loader;
	}

	/**
	 * @param url
	 * @return
	 */
	public static InputStream getInputStream(URL url) {
		try {
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			if (inputStream == null) {
				String error = "Unable to locate URL " + url;
				logger.error(error);
				throw new InitializationException(error);
			}

			return inputStream;
		} catch (IOException e) {
			String error = "Unable to retrieve file with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		}
	}

	/**
	 * @param filename
	 * @return
	 */
	public static Schema loadSchema(String filename) {
		InputStream inputStream = null;

		try {
			inputStream = CommonUtil.getInputStream(filename);
			StreamSource source = new StreamSource(inputStream);

			// Create schema
			SchemaFactory xsdFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = xsdFactory.newSchema(source);

			return schema;
		} catch (SAXException e) {
			String error = "Unable to schema " + filename + " with error " + e.getMessage();
			logger.error(error, e);
			throw new InitializationException(error, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * @param properties
	 */
	public static void logProperties(Properties properties) {
		StringBuilder builder = new StringBuilder();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		builder.append("\n").append("System properties:->>").append("\n");
		String leftPad = StringUtils.leftPad(" ", "System ".length() - 1);
		for (Entry<Object, Object> entry : entrySet) {
			builder.append(leftPad).append("|--${").append(entry.getKey()).append("}=").append(entry.getValue());
			builder.append("\n");
		}
		LogUtil.info(logger, "App system properties loading...{0}", builder.toString());
	}

	/**
	 * Return the unique identifier from the JVM with elimination of all "-".
	 * 
	 * @return
	 */
	public static String createGuid() {
		return DASH_PATTERN.matcher(UUID.randomUUID().toString()).replaceAll("");
	}

	private static String			ipAddress;
	private static ReentrantLock	ipAddressLock	= new ReentrantLock();

	/**
	 * Return the IP address of the machine running this application.
	 * 
	 * @return
	 */
	public static String getIpAddress() {
		if (ipAddress == null) {
			ipAddressLock.lock();
			try {
				if (ipAddress == null) {
					ipAddress = DEFAULT_LOCAL_IPADDRESS;
					try {
						InetAddress address = InetAddress.getLocalHost();
						ipAddress = address.getHostAddress();
					} catch (UnknownHostException e) {
						logger.warn("Unable to get hostAddress with error " + e.getMessage(), e);
					}
				}
			} finally {
				ipAddressLock.unlock();
			}
		}

		return ipAddress;
	}

	private static String			pid;
	private static ReentrantLock	pidLock	= new ReentrantLock();

	/**
	 * Return the process id for the application.
	 * 
	 * @return
	 */
	public static String getPid() {
		if (pid == null) {
			pidLock.lock();
			try {
				if (pid == null) {
					pid = DEFAULT_PID;
					try {
						RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
						if (runtime != null) {
							String name = runtime.getName();
							int index = name.indexOf('@');
							if (index >= 0) {
								pid = name.substring(0, index);
							}
						}
					} catch (Exception e) {
						logger.warn("Unable to get runtimeName with error " + e.getMessage(), e);
					}
				}
			} finally {
				pidLock.unlock();
			}
		}

		return pid;
	}

	private static String			hostName;
	private static ReentrantLock	hostNameLock	= new ReentrantLock();

	/**
	 * Return the name of the machine running this application.
	 * 
	 * @return
	 */
	public static String getHostName() {
		if (hostName == null) {
			hostNameLock.lock();
			try {
				if (hostName == null) {
					try {
						InetAddress address = InetAddress.getLocalHost();
						hostName = address.getHostName();
					} catch (UnknownHostException e) {
						logger.warn("Unable to resolve hostname: " + e.getMessage(), e);
						hostName = DEFAULT_LOCAL_HOSTNAME;
					}
				}
			} finally {
				hostNameLock.unlock();
			}
		}

		return hostName;
	}

	public static String bytesToHex(byte[] value) {
		StringBuilder builder = new StringBuilder();
		for (byte data : value) {
			builder.append(byteToHexChar(data >> 4));
			builder.append(byteToHexChar(data));
		}

		return builder.toString();
	}

	private static char byteToHexChar(int data) {
		char hex = '0';
		data = data & 0xf;
		switch (data) {
			case 0:
				hex = '0';
				break;
			case 1:
				hex = '1';
				break;
			case 2:
				hex = '2';
				break;
			case 3:
				hex = '3';
				break;
			case 4:
				hex = '4';
				break;
			case 5:
				hex = '5';
				break;
			case 6:
				hex = '6';
				break;
			case 7:
				hex = '7';
				break;
			case 8:
				hex = '8';
				break;
			case 9:
				hex = '9';
				break;
			case 10:
				hex = 'A';
				break;
			case 11:
				hex = 'B';
				break;
			case 12:
				hex = 'C';
				break;
			case 13:
				hex = 'D';
				break;
			case 14:
				hex = 'E';
				break;
			default:
				hex = 'F';
				break;
		}

		return hex;
	}
}
