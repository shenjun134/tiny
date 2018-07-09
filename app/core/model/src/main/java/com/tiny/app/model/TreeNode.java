/**
 * TreeNode.java
 *
 *
 */
package com.tiny.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.tiny.common.base.ToString;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class TreeNode extends ToString {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 3155526151996260957L;

	private String					name;

	private List<TreeNode>			children			= new ArrayList<TreeNode>();

	private Map<String, TreeNode>	childMap			= new HashMap<String, TreeNode>();

	/**
	 * 
	 */
	public TreeNode() {

	}

	/**
	 * @param name
	 */
	public TreeNode(String name) {
		this.name = name;
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
	 * @return the children
	 */
	public List<TreeNode> getChildren() {
		return children;
	}

	/**
	 * @param node
	 */
	public void addChild(TreeNode node) {
		children.add(node);
	}

	public String getAllItem() {
		return getAllItemBy(",");
	}
	
	public String getAllItemByEnter() {
		return getAllItemBy("\n");
	}

	public String getAllItemBy(String endwith){
		StringBuilder builder = new StringBuilder();
		builder.append(name).append(endwith);
		if (CollectionUtils.isNotEmpty(this.children)) {
			for (TreeNode model : this.children) {
				builder.append(getChildAllItem(model, endwith));
			}
		}
		return builder.toString();
	}
	
	/**
	 * @param model
	 * @return
	 */
	public String getChildAllItem(TreeNode model, String endwith) {
		StringBuilder builder = new StringBuilder();
		builder.append(model.name).append(endwith);
		for (TreeNode cModel : model.children) {
			builder.append(getChildAllItem(cModel, endwith));
		}
		return builder.toString();
	}

	public String getChildInfo() {
		StringBuilder builder = new StringBuilder();
		for (TreeNode model : children) {
			builder.append("|--").append(getInfo(model));
		}
		return builder.toString();
	}

	/**
	 * @param model
	 * @return
	 */
	private String getInfo(TreeNode model) {
		StringBuilder builder = new StringBuilder();
		builder.append(model.name).append("<br>");
		for (TreeNode child : model.children) {
			builder.append("|--").append(getInfo(child)).append("<br>");
		}
		return builder.toString();
	}

	public Map<String, TreeNode> getChildMap() {
		return childMap;
	}

	public void setChildMap(Map<String, TreeNode> childMap) {
		this.childMap = childMap;
	}
}
