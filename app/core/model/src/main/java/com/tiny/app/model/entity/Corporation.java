package com.tiny.app.model.entity;


public class Corporation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer corpId;
	
	private String corpName;
	
	private String corpMail;

	public Integer getCorpId() {
		return corpId;
	}

	public void setCorpId(Integer corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpMail() {
		return corpMail;
	}

	public void setCorpMail(String corpMail) {
		this.corpMail = corpMail;
	}

}
