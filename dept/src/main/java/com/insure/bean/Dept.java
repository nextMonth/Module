package com.insure.bean;


public class Dept { 
	
	private String deptId;//机构编码 
	private String deptName;//机构名称 
	private String kind;	//机构类型 
	private String branchId;//分公司 
	private String abvbranch;//上级机构 
	private String isUsed;//使用状态
	private Integer transTime;//交易间隔
	private Integer transDataTimes;//限制笔数
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchid) {
		this.branchId = branchid;
	}
	public String getAbvbranch() {
		return abvbranch;
	}
	public void setAbvbranch(String abvbranch) {
		this.abvbranch = abvbranch;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isused) {
		this.isUsed = isused;
	}
	 
	public Integer getTransTime() {
		return transTime;
	}
	public void setTransTime(Integer transTime) {
		this.transTime = transTime;
	}
	public Integer getTransDataTimes() {
		return transDataTimes;
	}
	public void setTransDataTimes(Integer transDataTimes) {
		this.transDataTimes = transDataTimes;
	}
	@Override
	public String toString() {
		return "Dept [deptId=" + deptId + ", deptName=" + deptName + ", kind="
				+ kind + ", branchId=" + branchId + ", abvbranch=" + abvbranch
				+ ", isUsed=" + isUsed + ", transTime=" + transTime
				+ ", transDataTimes=" + transDataTimes + "]";
	} 
	
}
