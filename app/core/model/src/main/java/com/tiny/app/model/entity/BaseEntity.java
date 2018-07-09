package com.tiny.app.model.entity;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Boolean isDeleted;

	protected Integer creator;
	
	protected Integer modifier;
	
	protected Date gmtCreate;
	
	protected Date gmtModified;
	
	
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
