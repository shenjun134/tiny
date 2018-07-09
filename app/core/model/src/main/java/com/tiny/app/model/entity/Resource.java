package com.tiny.app.model.entity;

public class Resource extends BaseEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				resId;

	private String				resKey;

	private String				realValue;

	private String				displayValue;

	private Integer				resOrder;

	private Integer				parentId;

	private String				remark;

	private Resource			parentResource;

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getRealValue() {
		return realValue;
	}

	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Integer getResOrder() {
		return resOrder;
	}

	public void setResOrder(Integer resOrder) {
		this.resOrder = resOrder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Resource getParentResource() {
		return parentResource;
	}

	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
