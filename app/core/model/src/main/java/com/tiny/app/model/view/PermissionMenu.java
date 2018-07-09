package com.tiny.app.model.view;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class PermissionMenu extends Checked {

	/** serialVersionUID */
	private static final long	serialVersionUID	= -8374079731996502698L;

	private Integer				permisId;

	private String				permisName;

	private String				permisCode;

	private String				permisUrl;

	private Integer				permisOrder;

	private Character			permisType;

	private String				imageUrl;

	private Character			showType;

	private Integer				showLevel;

	private Integer				assignLevel;

	private String				remark;

	/**
	 * ������
	 */
	public PermissionMenu() {
	}

	/**
	 * ������
	 * 
	 * @param id
	 * @param name
	 * @param value
	 * @param checked
	 */
	public PermissionMenu(long id, String name, String value, Boolean checked) {
		super(id, name, value, checked);
	}

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
	public Character getPermisType() {
		return permisType;
	}

	/**
	 * Setter method for property <tt>permisType</tt>.
	 * 
	 * @param permisType value to be assigned to property permisType
	 */
	public void setPermisType(Character permisType) {
		this.permisType = permisType;
	}

	/**
	 * Getter method for property <tt>imageUrl</tt>.
	 * 
	 * @return property value of imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Setter method for property <tt>imageUrl</tt>.
	 * 
	 * @param imageUrl value to be assigned to property imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Getter method for property <tt>showType</tt>.
	 * 
	 * @return property value of showType
	 */
	public Character getShowType() {
		return showType;
	}

	/**
	 * Setter method for property <tt>showType</tt>.
	 * 
	 * @param showType value to be assigned to property showType
	 */
	public void setShowType(Character showType) {
		this.showType = showType;
	}

	/**
	 * Getter method for property <tt>remark</tt>.
	 * 
	 * @return property value of remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Setter method for property <tt>remark</tt>.
	 * 
	 * @param remark value to be assigned to property remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * Getter method for property <tt>assignLevel</tt>.
	 * 
	 * @return property value of assignLevel
	 */
	public Integer getAssignLevel() {
		return assignLevel;
	}

	/**
	 * Setter method for property <tt>assignLevel</tt>.
	 * 
	 * @param assignLevel value to be assigned to property assignLevel
	 */
	public void setAssignLevel(Integer assignLevel) {
		this.assignLevel = assignLevel;
	}

}
