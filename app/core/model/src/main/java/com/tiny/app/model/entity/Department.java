package com.tiny.app.model.entity;

import java.util.Set;

public class Department extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer deptId;
	
	private String deptName;
	
	private String deptMail;
	
	private Integer ownerId;
	
	private Integer parentDeptId;
	
	private Integer corpId;
	
	private String remark;
	
	private Set<Corporation> corporation;
	private Set<Department> parentDepartment;
	
	private Set<User> owner;
	
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptMail() {
		return deptMail;
	}

	public void setDeptMail(String deptMail) {
		this.deptMail = deptMail;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(Integer parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

	public Integer getCorpId() {
		return corpId;
	}

	public void setCorpId(Integer corpId) {
		this.corpId = corpId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<Corporation> getCorporation() {
		return corporation;
	}

	public void setCorporation(Set<Corporation> corporation) {
		this.corporation = corporation;
	}

	public Set<Department> getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Set<Department> parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public Set<User> getOwner() {
		return owner;
	}

	public void setOwner(Set<User> owner) {
		this.owner = owner;
	}

	
	
}
