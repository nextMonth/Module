package com.insure.bean;

import java.util.List;
 


public class DeptTree extends Dept {
	
	private String id;
	
	private String text;
	
	private String state;//状态
	
	private List<DeptTree> children;

	public List<DeptTree> getChildren() {
		return children;
	}

	public void setChildren(List<DeptTree> children) {
		this.children = children;
	}

	
	
	@Override
	public String toString() {
		return "DeptTree [children=" + children + ", getDeptId()="
				+ getDeptId() + ", getDeptName()=" + getDeptName()
				+ ", getKind()=" + getKind() + ", getBranchid()="
				+ getBranchId() + ", getAbvbranch()=" + getAbvbranch()
				+ ", getIsused()=" + getIsUsed() + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
