package com.insure.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.base.dao.IBaseDao;
import com.base.dao.impl.BaseDaoImpl;
import com.insure.bean.Dept;
import com.insure.dao.IDeptDao;


@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements IDeptDao {
 
	
	@Override
	public void delCascade(Dept dept) {
		 
		List<Dept> deptList = getChildrenNode(dept);
		if(deptList != null){
			for(Dept unit:deptList){
				delCascade(unit);
			}
			
		}
		System.out.println("级联删除:" + dept);
		delete(dept);
		
	}

	 
	@Override
	public List<Dept> getChildrenNode(Dept dept) {
		String hql = "FROM Dept d WHERE d.abvbranch='" + dept.getDeptId() +"'"; 
		return find(hql);
	}
	
	 
 
}
