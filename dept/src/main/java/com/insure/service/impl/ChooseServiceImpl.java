package com.insure.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insure.bean.Dept;
import com.insure.dao.IDeptDao;
import com.insure.service.IChooseService;


@Transactional
@Service("chooseService")
public class ChooseServiceImpl implements IChooseService { 
	
	@Autowired
	private IDeptDao deptDao;
	 
	Map<String, Object> mapParam = new HashMap<String, Object>();
 
	@Override
	public List<Dept> getByAbvbranch(String abvbranch) {
		mapParam.clear(); 
	 	String hql = "FROM Dept WHERE abvbranch= :abvbranch";	
	 	mapParam.put("abvbranch", abvbranch);
		return deptDao.find(hql,mapParam);
	} 
 
	@Override
	public List<Dept> getByKind(String kind) {
		mapParam.clear();
		String hql = "FROM Dept WHERE kind= :kind";	
		mapParam.put("kind", kind);
		return deptDao.find(hql,mapParam); 
	 
	}

}
