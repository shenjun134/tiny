/**
 * OperatorDO.java
 *
 * Oct 26, 2016 - 3:45:00 PM
 *
 * "lemon-common-dal
 *
 */
package com.tiny.common.dal.dataobject;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class OperatorDO extends BaseDO {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8391480578320789204L;

	private Long				id;

	private String				name;

	private String				comments;

	private String				host;

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
