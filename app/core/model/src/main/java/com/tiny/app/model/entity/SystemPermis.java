/**
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.tiny.app.model.entity;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class SystemPermis extends BaseEntity {

	/** serialVersionUID */
	private static final long	serialVersionUID	= 2230787030152979569L;

	private Integer				permisId;

	private String				permisName;

	private String				permisCode;

	private String				permisUrl;

	private String				permisType;

	private Integer				permisOrder;

	private Integer				showLevel;

	private Integer				parentId;

	private String				parentCode;

	private String				serviceCode;

	private String				memo;

	/**
	 * Getter method for property <tt>permisId</tt>.
	 * 
	 * @return property value of permisId
	 */
	public Integer getPermisId() {
		return permisId;
	}

	/**
	 * Setter method for property <tt>permisId</tt>.
	 * 
	 * @param permisId value to be assigned to property permisId
	 */
	public void setPermisId(Integer permisId) {
		this.permisId = permisId;
	}

	/**
	 * Getter method for property <tt>permisName</tt>.
	 * 
	 * @return property value of permisName
	 */
	public String getPermisName() {
		return permisName;
	}

	/**
	 * Setter method for property <tt>permisName</tt>.
	 * 
	 * @param permisName value to be assigned to property permisName
	 */
	public void setPermisName(String permisName) {
		this.permisName = permisName;
	}

	/**
	 * Getter method for property <tt>permisCode</tt>.
	 * 
	 * @return property value of permisCode
	 */
	public String getPermisCode() {
		return permisCode;
	}

	/**
	 * Setter method for property <tt>permisCode</tt>.
	 * 
	 * @param permisCode value to be assigned to property permisCode
	 */
	public void setPermisCode(String permisCode) {
		this.permisCode = permisCode;
	}

	/**
	 * Getter method for property <tt>permisUrl</tt>.
	 * 
	 * @return property value of permisUrl
	 */
	public String getPermisUrl() {
		return permisUrl;
	}

	/**
	 * Setter method for property <tt>permisUrl</tt>.
	 * 
	 * @param permisUrl value to be assigned to property permisUrl
	 */
	public void setPermisUrl(String permisUrl) {
		this.permisUrl = permisUrl;
	}

	/**
	 * Getter method for property <tt>permisType</tt>.
	 * 
	 * @return property value of permisType
	 */
	public String getPermisType() {
		return permisType;
	}

	/**
	 * Setter method for property <tt>permisType</tt>.
	 * 
	 * @param permisType value to be assigned to property permisType
	 */
	public void setPermisType(String permisType) {
		this.permisType = permisType;
	}

	/**
	 * Getter method for property <tt>permisOrder</tt>.
	 * 
	 * @return property value of permisOrder
	 */
	public Integer getPermisOrder() {
		return permisOrder;
	}

	/**
	 * Setter method for property <tt>permisOrder</tt>.
	 * 
	 * @param permisOrder value to be assigned to property permisOrder
	 */
	public void setPermisOrder(Integer permisOrder) {
		this.permisOrder = permisOrder;
	}

	/**
	 * Getter method for property <tt>showLevel</tt>.
	 * 
	 * @return property value of showLevel
	 */
	public Integer getShowLevel() {
		return showLevel;
	}

	/**
	 * Setter method for property <tt>showLevel</tt>.
	 * 
	 * @param showLevel value to be assigned to property showLevel
	 */
	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	/**
	 * Getter method for property <tt>parentId</tt>.
	 * 
	 * @return property value of parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * Setter method for property <tt>parentId</tt>.
	 * 
	 * @param parentId value to be assigned to property parentId
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * Getter method for property <tt>serviceCode</tt>.
	 * 
	 * @return property value of serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * Setter method for property <tt>serviceCode</tt>.
	 * 
	 * @param serviceCode value to be assigned to property serviceCode
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * Getter method for property <tt>memo</tt>.
	 * 
	 * @return property value of memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * Setter method for property <tt>memo</tt>.
	 * 
	 * @param memo value to be assigned to property memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * Getter method for property <tt>parentCode</tt>.
	 * 
	 * @return property value of parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * Setter method for property <tt>parentCode</tt>.
	 * 
	 * @param parentCode value to be assigned to property parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
