package com.tiny.app.model.entity;

import java.util.Date;

public class UserLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private Long				logId;

	private Date				gmtCreate;

	private Integer				userId;

	private String				topic;

	private String				remark;

	private String				result;

	private String				comment;

	private User				user;

	public UserLog() {
	}

	/**
	 * ������־
	 * 
	 * @param userId
	 * @param topic
	 */
	public UserLog(Integer userId, String topic) {
		this.gmtCreate = new Date();
		this.userId = userId;
		this.topic = topic;
	}

	/**
	 * ������־
	 * 
	 * @param userId
	 * @param topic
	 * @param remark
	 */
	public UserLog(Integer userId, String topic, String remark) {
		this.gmtCreate = new Date();
		this.userId = userId;
		this.topic = topic;
		this.remark = remark;
	}

	/**
	 * ������־
	 * 
	 * @param userId
	 * @param topic
	 * @param result
	 * @param remark
	 */
	public UserLog(Integer userId, String topic, String result, String remark) {
		this.userId = userId;
		this.topic = topic;
		this.result = result;
		this.remark = remark;
		this.gmtCreate = new Date();
	}

	/**
	 * ������־
	 * 
	 * @param userId
	 * @param topic
	 * @param result
	 * @param remark
	 * @param comment
	 */
	public UserLog(Integer userId, String topic, String result, String remark, String comment) {
		this.userId = userId;
		this.topic = topic;
		this.result = result;
		this.remark = remark;
		this.comment = comment;
		this.gmtCreate = new Date();
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
