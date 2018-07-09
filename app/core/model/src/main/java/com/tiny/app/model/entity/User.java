package com.tiny.app.model.entity;

import java.util.Set;

public class User extends BaseEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				userId;

	private String				loginId;

	private String				userName;

	private String				passwd;

	private String				email;

	private Integer				deptId;

	private String				workNo;

	private String				phone;

	private String				mobile;

	private String				alitalkType;

	private String				alitalkId;

	private Boolean				isEnabled;

	private String				remark;

	private Set<Department>		department;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAlitalkType() {
		return alitalkType;
	}

	public void setAlitalkType(String alitalkType) {
		this.alitalkType = alitalkType;
	}

	public String getAlitalkId() {
		return alitalkId;
	}

	public void setAlitalkId(String alitalkId) {
		this.alitalkId = alitalkId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Set<Department> getDepartment() {
		return department;
	}

	public void setDepartment(Set<Department> department) {
		this.department = department;
	}

}
