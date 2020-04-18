package com.insure.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insure.bean.Dept;
import com.insure.bean.DeptTree;
import com.insure.bean.ResultInfo;
import com.insure.dao.IDeptDao;
import com.insure.service.IDeptService;

 
@Transactional
// 启用事务机制
@Service("deptService")
public class DeptServiceImpl implements IDeptService {

	private static final Log logger = LogFactory.getLog(DeptServiceImpl.class);

	@Autowired
	private IDeptDao deptDao;

	private ResultInfo info = new ResultInfo(false, "");  
	
	@Override
	public List<Dept> queryByLevel(String levelStr) {
		String constr = " WHERE 1=1";
		if ("1".equals(levelStr)) {
			constr = constr + " AND t.kind='0'";
		} else if ("2".equals(levelStr)) {
			constr = constr + " AND t.kind in ('0','1')";
		} else if ("3".equals(levelStr)) {
			constr = constr + " AND t.kind in ('0','1','2')";
		}

		List<Dept> deptList = new ArrayList<Dept>();
		String hql = "FROM Dept t" + constr;
		deptList = deptDao.find(hql);
		return deptList;
	}

	@Override
	public List<DeptTree> queryTreeByLevel(String levelstr) {
		List<Dept> depts = queryByLevel(levelstr);
		List<DeptTree> treeList = new ArrayList<DeptTree>();
		for (Dept dept : depts) {
			DeptTree deptTree = new DeptTree();
			deptTree.setAbvbranch(dept.getAbvbranch());
			deptTree.setDeptId(dept.getDeptId());
			deptTree.setDeptName(dept.getDeptName());
			deptTree.setKind(dept.getKind());
			deptTree.setBranchId(dept.getBranchId());
			deptTree.setId(dept.getDeptId());
			deptTree.setText(dept.getDeptName());
			deptTree.setIsUsed(dept.getIsUsed());
			deptTree.setTransTime(dept.getTransTime());
			deptTree.setTransDataTimes(dept.getTransDataTimes()); 
			treeList.add(deptTree);
		}
		List<DeptTree> newTreeList = getfatherNode(treeList);

		return newTreeList;
	}

	private final static List<DeptTree> getfatherNode(
			List<DeptTree> treeDataList) {
		List<DeptTree> newTreeDataList = new ArrayList<DeptTree>();
		List<DeptTree> tempDept =  new ArrayList<DeptTree>();
		for (DeptTree jsonTreeData : treeDataList) {
			if (jsonTreeData.getAbvbranch() == null ||"".equals(jsonTreeData.getAbvbranch())) {
				// 获取父节点下的子节点
				tempDept = getChildrenNode(
						jsonTreeData.getDeptId(), treeDataList); 
				if(tempDept != null && tempDept.size()>0){
					jsonTreeData.setState("closed");
					jsonTreeData.setChildren(tempDept);
				}
				
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	private final static List<DeptTree> getChildrenNode(String pid,
			List<DeptTree> treeDataList) {
		List<DeptTree> newTreeDataList = new ArrayList<DeptTree>();
		List<DeptTree> temp = null;
		for (DeptTree jsonTreeData : treeDataList) {
			 temp = new ArrayList<DeptTree>();
			if (jsonTreeData.getAbvbranch() == null)
				continue;
			// 这是一个子节点
			if (jsonTreeData.getAbvbranch().equals(pid)) {
				// 递归获取子节点下的子节点
				temp = getChildrenNode(
						jsonTreeData.getDeptId(), treeDataList);
				if(!temp.isEmpty()){
					jsonTreeData.setState("closed"); 
					jsonTreeData.setChildren(temp);
				}
				
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	@Override
	public ResultInfo add(Dept dept) {
		logger.info("机构信息: " + dept);
		Dept queryDept = get(dept);
		if (queryDept == null) {
			// 分公司判断
			if ("0".equals(dept.getKind())) {
				dept.setBranchId("");
			} else if ("1".equals(dept.getKind())) {
				dept.setBranchId(dept.getDeptId());
			} else {
				Dept abvDept = deptDao.get(Dept.class, dept.getAbvbranch());
				dept.setBranchId(abvDept.getBranchId());
			}
			deptDao.save(dept);
			info.setIsSuccess(true);
			info.setMessage("添加成功");
			return info;
		} else {
			info.setIsSuccess(false);
			info.setMessage("添加失败,机构代码已存在");
			return info;
		}

	}

	@Override
	public ResultInfo update(Dept dept) {
		logger.info("dept: " + dept);
		Dept queryDept = get(dept);
		queryDept.setDeptName(dept.getDeptName());
		queryDept.setIsUsed(dept.getIsUsed());
		queryDept.setTransTime(dept.getTransTime());
		queryDept.setTransDataTimes(dept.getTransDataTimes());
		deptDao.update(queryDept);
		info.setIsSuccess(true);
		info.setMessage("修改成功");
		return info;
	}

	@Override
	public ResultInfo delete(Dept dept) { 
		Dept queryDept = get(dept);
		Map<String,Object> mapParam = new HashMap<String, Object>();
		String sql="FROM Dept d WHERE d.abvbranch= :deptId"; 
		mapParam.put("deptId", queryDept.getDeptId());
		List<Dept> deptList=deptDao.find(sql,mapParam); 
		if (queryDept != null  &&deptList.isEmpty()) {
			deptDao.delCascade(queryDept);
			info.setIsSuccess(true);
			info.setMessage("删除成功");
			return info;
		}
		String message = "";  
		if(deptList.size()>0){
			message= "该机构有下级机构,禁止删除";
		} 
		info.setIsSuccess(false);
		info.setMessage(message);
		return info;
	}

	@Override
	public Dept get(Dept dept) { 
		Dept queryDept = deptDao.get(Dept.class, dept.getDeptId());
		return queryDept;
	}

	@Override
	public List<Dept> query(Dept dept) {
		Map<String,Object> mapParam = new HashMap<String, Object>();
		String hql = "FROM Dept d WHERE 1=1";
		if (!"".equals(dept.getDeptId())) {
			hql = hql + " AND d.deptId like :deptId";
			mapParam.put("deptId", dept.getDeptId() + "%");
		}
		if (!"".equals(dept.getDeptName())) {
			hql = hql + " AND d.deptName like :deptName";
			mapParam.put("deptName","%" + dept.getDeptName() + "%");
		}

		return deptDao.find(hql,mapParam);
	}

	@Override
	public Dept getBySonId(String deptId) {
		Map<String,Object> mapParam = new HashMap<String, Object>();
		String hql = "FROM Dept d WHERE d.deptId= :deptId";
		mapParam.put("deptId", deptId);
		Dept dept = deptDao.get(hql, mapParam);

		String hqlParent = "FROM Dept d WHERE d.deptId=:deptId";
		mapParam.put("deptId", dept.getAbvbranch());
		Dept deptParent = deptDao.get(hqlParent, mapParam);

		return deptParent;
	}

	@Override
	public Dept get(String deptId) {
		 return deptDao.get(Dept.class, deptId);
	}

}
