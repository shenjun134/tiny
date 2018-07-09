/**
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.tiny.core.model;

import java.util.HashSet;
import java.util.Set;

import com.tiny.common.base.ToString;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class WebUser extends ToString {

	/**
	 * serialVersionUID
	 */
	private static final long	serialVersionUID	= -4359024905506372704L;

	private Long				userId;

	private String				loginId;

	private String				workNo;

	private String				userName;

	/**
	 * 
	 */
	private String				email;

	private String				contact;

	private String				password;

	/**
	 * confirm passowrd
	 */
	private String				cpassword;

	private Boolean				isLogined			= false;

	private Boolean				isNeedPermis		= true;

	private Boolean				forbidIP			= true;

	private Boolean				hasNoticePermis		= false;

	private Set<String>			permisSet			= new HashSet<String>();

	public WebUser() {

	}

	public WebUser(Long userId) {
		this.userId = userId;
	}

	public WebUser(String loginId, String userName) {
		this.loginId = loginId;
		this.userName = userName;
	}

	/**
	 * Ĭ�Ϲ�����
	 */
	public WebUser(String guestInfo) {
		String[] guestUser = guestInfo.split("\\|");
		this.userId = Long.parseLong(guestUser[0]);
		this.loginId = (String) guestUser[1];
		this.workNo = (String) guestUser[2];
		this.userName = (String) guestUser[3];
		this.isLogined = Boolean.valueOf(guestUser[4]);
		this.isNeedPermis = Boolean.valueOf(guestUser[6]);
	}

	/**
	 * ������
	 * 
	 * @param userId
	 * @param loginId
	 * @param workNo
	 * @param userName
	 */
	public WebUser(Long userId, String loginId, String workNo, String userName) {
		this.userId = userId;
		this.loginId = loginId;
		this.workNo = workNo;
		this.userName = userName;
	}

	/**
	 * Getter method for property <tt>userId</tt>.
	 * 
	 * @return property value of userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Setter method for property <tt>userId</tt>.
	 * 
	 * @param userId value to be assigned to property userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Getter method for property <tt>loginId</tt>.
	 * 
	 * @return property value of loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * Setter method for property <tt>loginId</tt>.
	 * 
	 * @param loginId value to be assigned to property loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * Getter method for property <tt>workNo</tt>.
	 * 
	 * @return property value of workNo
	 */
	public String getWorkNo() {
		return workNo;
	}

	/**
	 * Setter method for property <tt>workNo</tt>.
	 * 
	 * @param workNo value to be assigned to property workNo
	 */
	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	/**
	 * Getter method for property <tt>userName</tt>.
	 * 
	 * @return property value of userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter method for property <tt>userName</tt>.
	 * 
	 * @param userName value to be assigned to property userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsLogined() {
		return isLogined;
	}

	public void setIsLogined(Boolean isLogined) {
		this.isLogined = isLogined;
	}

	/**
	 * Getter method for property <tt>isNeedPermis</tt>.
	 * 
	 * @return property value of isNeedPermis
	 */
	public Boolean getIsNeedPermis() {
		return isNeedPermis;
	}

	/**
	 * Setter method for property <tt>isNeedPermis</tt>.
	 * 
	 * @param isNeedPermis value to be assigned to property isNeedPermis
	 */
	public void setIsNeedPermis(Boolean isNeedPermis) {
		this.isNeedPermis = isNeedPermis;
	}

	/**
	 * Getter method for property <tt>forbidIP</tt>.
	 * 
	 * @return property value of forbidIP
	 */
	public Boolean getForbidIP() {
		return forbidIP;
	}

	/**
	 * Setter method for property <tt>forbidIP</tt>.
	 * 
	 * @param forbidIP value to be assigned to property forbidIP
	 */
	public void setForbidIP(Boolean forbidIP) {
		this.forbidIP = forbidIP;
	}

	/**
	 * Getter method for property <tt>hasNoticePermis</tt>.
	 * 
	 * @return property value of hasNoticePermis
	 */
	public Boolean getHasNoticePermis() {
		return hasNoticePermis;
	}

	/**
	 * Setter method for property <tt>hasNoticePermis</tt>.
	 * 
	 * @param hasNoticePermis value to be assigned to property hasNoticePermis
	 */
	public void setHasNoticePermis(Boolean hasNoticePermis) {
		this.hasNoticePermis = hasNoticePermis;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getPermisSet() {
		return permisSet;
	}

	public void setPermisSet(Set<String> permisSet) {
		this.permisSet = permisSet;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
