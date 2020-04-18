package com.insure.bean;

/**   
 * 机构等级
 * 0 总公司  1 分公司 2 市公司 3 承保机构   
 *         
 */
public class DeptLevel {
	
	private String levelId;
	
	private String levelName;
	
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public DeptLevel(String levelId, String levelName) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
	}
	public DeptLevel() {
		super();
	 
	}

}
