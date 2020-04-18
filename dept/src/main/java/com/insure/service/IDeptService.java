package com.insure.service;

import java.util.List;

import com.insure.bean.Dept;
import com.insure.bean.DeptTree;
import com.insure.bean.ResultInfo;


public interface IDeptService {
	
	/**
	 * 通过主键获取数据库对象  
	 * @param  承保机构记录  
	 * @return 数据库获取的承保机构对象
     */
	public Dept get(String deptId);
	 
    /**
     * 
    *查询承保机构树形结构   
    * @param   
    * @return   返回树形结构化数据
    * @throws
     */
	public List<DeptTree> queryTreeByLevel(String level); 
	
	/**
	 * 
	* 查询机构类型之上的机构列表    
	* @param  机构类型
	* @return 承保机构列表
	* @throws
	 */
	public List<Dept> queryByLevel(String level);
	
	/**
	 * 添加承保机构记录 
	 * @param  承保机构对象
	 * @return  执行结果
	 */
	public ResultInfo add(Dept dept);
	
	/**
	 * 修改承保机构对象记录    
	 * @param  承保机构对象
	 * @return 执行结果
	 */
	public ResultInfo update(Dept dept);
	
	/**
	 * 删除承保机构记录  
 	 * @param 承保机构对象   
	 * @return 执行结果
	 */
	public ResultInfo delete(Dept dept);
	
	/**
	 * 通过主键获取数据库对象  
	 * @param  承保机构记录  
	 * @return 数据库获取的承保机构对象
     */
	public Dept get(Dept dept);
	
	/**
	 * 通过机构代码、机构名称获取机构集合
	 */
    public List<Dept> query(Dept dept);
    
    /**
     * 查询承保机构   
     * @param  承保机构代码 
     * @return 上级承保机构对象
     */
    public Dept getBySonId(String deptId);
	
}
