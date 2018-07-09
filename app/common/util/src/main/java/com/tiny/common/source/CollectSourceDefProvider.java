/**
 * CollectSourceDefProvider.java
 *
 * Dec 19, 2016 - 4:17:05 PM
 *
 * "lemon-common-util
 *
 */
package com.tiny.common.source;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tiny.common.configuration.ConfigurationManager;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class CollectSourceDefProvider implements MessageListener {

	private static final Logger		logger			= Logger.getLogger(CollectSourceDefProvider.class);

	/**
	 * 
	 */
	private DataSourceFactory		dataSourceFactory;

	private Map<String, DataSource>	dataSourceMap	= new ConcurrentHashMap<String, DataSource>();

	private ConfigurationManager	configurationManager;

	/**
	 * 
	 */
	public void init() {

		String dynamicPros = configurationManager.getString("dynamic.DB.key");
		if (StringUtils.isBlank(dynamicPros)) {
			return;
		}
		String[] keyArr = StringUtils.split(dynamicPros, ",");

		for (String temp : keyArr) {
			DataSourceDefinition dsd = getJdbcProperties(temp);
			if (dsd != null) {
				addDataSourceToMem(dsd);
			}
		}
	}

	/**
	 * 
	 */
	public void destroy() {
		for (DataSource dataSource : dataSourceMap.values()) {
			dataSourceFactory.destroyDataSource(dataSource);
		}
	}

	/**
	 * @param dataSourceDefinition
	 */
	private void addDataSourceToMem(DataSourceDefinition dataSourceDefinition) {
		try {
			DataSource dataSource = dataSourceFactory.getDataSource(dataSourceDefinition);
			DataSource oldDataSource = dataSourceMap.put(dataSourceDefinition.getKey(), dataSource);
			logger.info("Replace old data source " + oldDataSource + " to new data source " + dataSource);
			if (oldDataSource != null) {
				dataSourceFactory.destroyDataSource(oldDataSource);
			}
		} catch (Exception e) {
			logger.error("Failed to add datsource #" + dataSourceDefinition.getKey() + " to memory");
		}
	}

	public DataSource getDataSource(String key) {

		DataSource datasource = null;
		if (dataSourceMap.containsKey(key)) {
			datasource = dataSourceMap.get(key);

			try {
				Connection conn = datasource.getConnection();
				conn.close();
				return datasource;
			} catch (SQLException sqle) {
				logger.error("Connection error when retrieving data key: " + key);
				return null;
			}
		}
		// // Lazy load when the data source is corrected and sampling task is active.
		// else {
		// CollectSourceDefinition collectSourceDefinition = metadataService.getCollectSource(sourceId);
		// if (!collectSourceDefinition.getSourceType()
		// .equalsIgnoreCase(CollectSourceType.DATA_SOURCE.getSourceType())) {
		// return null;
		// }
		// try {
		// datasource = dataSourceFactory.getDataSource((DataSourceDefinition) collectSourceDefinition);
		// Connection conn = datasource.getConnection();
		// conn.close();
		// addDataSourceToMem((DataSourceDefinition) collectSourceDefinition);
		// return datasource;
		// } catch (Exception sqle) {
		// LOGGER.error("Connection error when retrieving data source ID: " + sourceId);
		// return null;
		// }
		// }
		return null;
	}

	public void deleteDataSource(Integer sourceId) {

	}

	@Override
	public void onMessage(Message message) {/*
											 * ActionEvent actionEvent = ActionEventUtils.extractActionEvent(message);
											 * LOGGER.info("Receive a action event : " + actionEvent);
											 * CollectSourceDefinition collectSourceDefinition =
											 * ActionEventUtils.extractEventObject(actionEvent.getEvent(),
											 * CollectSourceDefinition.class); String sourceType =
											 * collectSourceDefinition.getSourceType(); if
											 * (StringUtils.equalsIgnoreCase(sourceType,
											 * CollectSourceType.DATA_SOURCE.getSourceType())) { DataSourceDefinition
											 * dataSourceDefinition =
											 * ActionEventUtils.extractEventObject(actionEvent.getEvent(),
											 * DataSourceDefinition.class); if
											 * (StringUtils.equalsIgnoreCase(actionEvent.getAction(), ActionEvent.ADD))
											 * { addDataSourceToMem(dataSourceDefinition); } else if
											 * (StringUtils.equalsIgnoreCase(actionEvent.getAction(),
											 * ActionEvent.REMOVE)) { DataSource removed =
											 * dataSourceMap.remove(dataSourceDefinition.getSourceId());
											 * LOGGER.info(removed + " was removed from memory"); } } else if
											 * (StringUtils.equalsIgnoreCase(sourceType,
											 * CollectSourceType.MQ_WEB.getSourceType())) { MQWebSourceDefinition
											 * mqWebSourceDefinition =
											 * ActionEventUtils.extractEventObject(actionEvent.getEvent(),
											 * MQWebSourceDefinition.class); if
											 * (StringUtils.equalsIgnoreCase(actionEvent.getAction(), ActionEvent.ADD))
											 * { addMQSourceToMem(mqWebSourceDefinition); } else if
											 * (StringUtils.equalsIgnoreCase(actionEvent.getAction(),
											 * ActionEvent.REMOVE)) { MQWebSourceDefinition removed =
											 * mqSource.remove(mqWebSourceDefinition.getSourceId()); LOGGER.info(removed
											 * + " was removed from memory"); } }
											 */
	}

	/**
	 * @param temp
	 * @return
	 */
	private DataSourceDefinition getJdbcProperties(String envCode) {
		// SIT.jdbc.driverClass=oracle.jdbc.OracleDriver
		// SIT.jdbc.url=jdbc:oracle:thin:@10.1.114.205:1753:O05GCE0
		// SIT.jdbc.username=OPGCEP2
		// SIT.jdbc.password=OPGCEP2
		// SIT.jdbc.keyhost=O05GCE0
		if (StringUtils.isBlank(envCode)) {
			return null;
		}
		String keyOfDriverClass = StringUtils.join(new String[] { envCode, "jdbc.driverClass" }, ".");
		String valOfDriverClass = configurationManager.getString(keyOfDriverClass);
		if (StringUtils.isBlank(valOfDriverClass)) {
			return null;
		}
		DataSourceDefinition dsd = new DataSourceDefinition(envCode);
		dsd.setJdbcDriver(valOfDriverClass);
		String keyOfUrl = StringUtils.join(new String[] { envCode, "jdbc.url" }, ".");
		dsd.setJdbcUrl(configurationManager.getString(keyOfUrl));
		String keyOfUsername = StringUtils.join(new String[] { envCode, "jdbc.username" }, ".");
		dsd.setUsername(configurationManager.getString(keyOfUsername));
		String keyOfPassword = StringUtils.join(new String[] { envCode, "jdbc.password" }, ".");
		dsd.setPassword(configurationManager.getString(keyOfPassword));
		String keyOfHost = StringUtils.join(new String[] { envCode, "jdbc.keyhost" }, ".");
		dsd.setHost(configurationManager.getString(keyOfHost));
		return dsd;

	}

	/**
	 * @return
	 */
	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

	/**
	 * @param dataSourceFactory
	 */
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	/**
	 * @return the configurationManager
	 */
	public ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	/**
	 * @param configurationManager the configurationManager to set
	 */
	public void setConfigurationManager(ConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

}
