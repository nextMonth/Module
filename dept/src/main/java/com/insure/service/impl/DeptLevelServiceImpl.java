package com.insure.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insure.bean.DeptLevel;
import com.insure.dao.IDeptLevelDao;
import com.insure.service.IDeptLevelService;


@Service("deptLevelService")
public class DeptLevelServiceImpl implements IDeptLevelService {

	@Autowired
	private IDeptLevelDao deptLevelDao;
	
 
	@Override
	public List<DeptLevel> getAll() {
		
		return  deptLevelDao.getAll();
	}

}
