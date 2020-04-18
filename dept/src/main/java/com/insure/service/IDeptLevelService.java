package com.insure.service;

import java.util.List;

import com.insure.bean.DeptLevel;


public interface IDeptLevelService {
	
	/**
	 * 构造、返回机构类型集合
	 *  
	 */
	public List<DeptLevel> getAll();

}
