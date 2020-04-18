package com.insure.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.insure.bean.DeptLevel;
import com.insure.dao.IDeptLevelDao;

@Repository("deptLevelDao")
public class DeptLevelDaoImpl implements IDeptLevelDao {

	
	@Override
	public List<DeptLevel> getAll() {
		List<DeptLevel> deptLevels = new ArrayList<DeptLevel>();
		deptLevels.add(new DeptLevel("0","总公司"));
		deptLevels.add(new DeptLevel("1","分公司"));
		deptLevels.add(new DeptLevel("2","市公司"));
		deptLevels.add(new DeptLevel("3","承保机构"));
		return deptLevels;
	}

}
