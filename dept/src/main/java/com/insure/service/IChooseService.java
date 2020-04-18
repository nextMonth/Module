package com.insure.service;

import java.util.List;

import com.insure.bean.Dept;


public interface IChooseService {
	
	/**
	 * 通过上级承保机构查询下级节点 
	 * @param 上级机构节点
	 * @return 承保机构集合
	 *  
	 */
	public List<Dept> getByAbvbranch(String abvbranch);
	 
	
	/**
	 * 获取当前级别的承保机构
	 *    
	 * @param  kind 机构级别
	 * @return 承保机构集合
	 */
	public List<Dept> getByKind(String kind);

}
