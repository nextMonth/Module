package com.insure.dao;

import java.util.List;

import com.base.dao.IBaseDao;
import com.insure.bean.Dept;


public interface IDeptDao extends IBaseDao<Dept> {
	
	/**
	 * 
	 * 递归删除本级及下属节点   
	 * @param  承保机构对象 
	 * @return  
	 */
    public void  delCascade(Dept dept);
    
    /**
     * 查询下属子节点（不含递归）   
     * @param   承保机构对象
     * @return  子节点集合    
     * @throws
     */
    public List<Dept> getChildrenNode(Dept dept);


}
