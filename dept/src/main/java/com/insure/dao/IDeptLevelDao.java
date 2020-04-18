package com.insure.dao;

import java.util.List;

import com.insure.bean.DeptLevel;


public interface IDeptLevelDao {
	
	/**
	 * 查询机构类型列表
	 * @return 全部机构类型
	 */
	public  List<DeptLevel> getAll();

}
