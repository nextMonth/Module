package com.insure.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insure.bean.Dept;
import com.insure.bean.DeptLevel;
import com.insure.bean.DeptTree;
import com.insure.bean.ResultInfo;
import com.insure.service.IDeptLevelService;
import com.insure.service.IDeptService;


@Controller
@RequestMapping("/dept")
public class DeptController {

	private static final Log logger = LogFactory.getLog(DeptController.class);

	@Autowired
	private IDeptService deptService;

	@Autowired
	private IDeptLevelService deptLevelService;

	private ResultInfo result = new ResultInfo(); 
	/**
	 * 承保机构初始页面
	 * 
	 * @param
	 * @return 承保机构文件名
	 */
	@RequestMapping("/index.do")
	public String getIndex() {
		return "dept/dept";
	}

	/**
	 * 查询树形承保机构
	 * 
	 * @return 树结构承保机构
	 */
	@RequestMapping("/getall.do")
	@ResponseBody
	public List<DeptTree> getDeptAll() { 
		List<DeptTree> DeptTreeList = deptService.queryTreeByLevel("");
		return DeptTreeList;
	}

	/**
	 * 查询承保机构的类型
	 * 
	 * @param
	 * @return 机构类型列表
	 */
	@ResponseBody
	@RequestMapping("/getdeptlevel.do")
	public List<DeptLevel> getlevel() {
		return deptLevelService.getAll();
	}

	/**
	 * 通过级别过滤树形承保机构
	 * 
	 * @param kind
	 *            机构级别
	 * @return 树形承保机构
	 */
	@ResponseBody
	@RequestMapping("/getdeptmap.do")
	public List<DeptTree> getDepts(@RequestParam(value = "kind", required = false) String kind) {
		logger.info("机构级别: " + kind);
		return deptService.queryTreeByLevel(kind);
	}

	/**
	 * 机构代码、机构名称查询承保机构
	 * 
	 * @param dept
	 *            查询条件对象
	 * @return 承保机构集合
	 */
	@ResponseBody
	@RequestMapping("/query.do")
	public List<Dept> queryDepts(Dept dept) {
		logger.info("查询条件: " + dept);
		List<Dept> depts = new ArrayList<Dept>();
		depts = deptService.query(dept);
		return depts;
	}

	/**
	 * 添加承保机构
	 * 
	 * @param dept
	 *            承保机构
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping("/add.do")
	public ResultInfo addDept(Dept dept) {
		logger.info("添加承保机构: " + dept);
		result = deptService.add(dept);
		return result;
	}

	/**
	 * 修改承保机构
	 * 
	 * @param dept
	 *            承保机构
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public ResultInfo updateDept(Dept dept) {
		logger.info("修改承保机构: " + dept);
		result = deptService.update(dept);
		return result;
	}

	/**
	 * 删除承保机构
	 * 
	 * @param dept
	 *            承保机构
	 * @return 执行结果
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public ResultInfo deleleDept(Dept dept) {
		logger.info("删除承保机构: " + dept);
		result = deptService.delete(dept); 
		return result;
	}

	/**
	 * 通过承保机构代码查询上级承保机构
	 * 
	 * @param deptId
	 *            承保机构代码
	 * @return 承保机构
	 */
	@ResponseBody
	@RequestMapping("/parent.do")
	public Dept getFather(String deptId) {
		logger.info("下级对象的机构代码: " + deptId);
		Dept parent = deptService.getBySonId(deptId);
		return parent;
	}

}
