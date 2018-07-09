/**
 * SystemConfigComponentImpl.java
 *
 * Sep 8, 2016 - 3:52:38 PM
 *
 * "lemon-core-service
 *
 */
package com.tiny.core.service.component.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tiny.app.model.entity.NameValue;
import com.tiny.common.base.CommonResult;
import com.tiny.common.configuration.ConfigurationManager;
import com.tiny.common.transaction.AbstractService;
import com.tiny.common.transaction.ServiceCallback;
import com.tiny.common.util.LogUtil;
import com.tiny.core.service.component.SystemConfigComponent;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SystemConfigComponentImpl extends AbstractService implements SystemConfigComponent {

	/**
	 * 
	 */
	private static final Logger	logger	= Logger.getLogger(SystemConfigComponentImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lemon.core.service.component.SystemConfigComponent#loadPropertis()
	 */
	@Override
	public List<NameValue> loadPropertis() {
		final CommonResult<List<NameValue>> baseResult = new CommonResult<List<NameValue>>();
		serviceTemplate.executeWithoutTransaction(baseResult, new ServiceCallback() {

			@Override
			public void doTransaction() {
				List<NameValue> list = new ArrayList<NameValue>();
				Properties properties = ConfigurationManager.getInstance().getProperties();
				Set<Entry<Object, Object>> entrySet = properties.entrySet();
				for (Entry<Object, Object> entry : entrySet) {
					String name = String.valueOf(entry.getKey());
					String value = String.valueOf(entry.getValue());
					if (StringUtils.contains(name, "password")) {
						value = "********************";
					}
					list.add(new NameValue(name, value));
				}
				baseResult.markeSuccess(list);
			}
		});
		LogUtil.debug(logger, "loadPropertis result:{0}", baseResult);
		return baseResult.getData();
	}

}
