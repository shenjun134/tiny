/**
 * OperatorModel.java
 *
 * Oct 12, 2016 - 5:07:45 PM
 *
 * "lemon-core-model
 *
 */
package com.tiny.core.model;

import com.tiny.common.base.ToString;
import com.tiny.common.util.CommonUtil;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class OperatorModel extends ToString {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5157924830766670829L;

	private Long				id;

	private String				name;

	private String				comments;

	private String				host;

	/**
	 * 
	 */
	public OperatorModel() {
	}

	public OperatorModel(Long id, String name, String comments, String host) {
		super();
		this.id = id;
		this.name = name;
		this.comments = comments;
		this.host = host;
	}

	public static OperatorModel getSystemOperator() {
		Long id = -1L;
		String name = "system";
		String comments = "system operate";
		String host = CommonUtil.getHostName() + "|" + CommonUtil.getIpAddress();
		return new OperatorModel(id, name, comments, host);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

}
