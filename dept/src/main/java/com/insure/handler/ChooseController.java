package com.insure.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.insure.bean.Dept;
import com.insure.service.IChooseService;
import com.insure.service.IDeptService;

 
@SessionAttributes({ "userId" })
@RequestMapping("/choose/")
@Controller
public class ChooseController {
	
	private static final Log logger = LogFactory.getLog(ChooseController.class);
	
	@Autowired
	private  IChooseService chooseService;  
	
	@Autowired
	private IDeptService deptService;
	
	 
	
	/**
	 * 查询下级承保机构 
	 *    
	 * @param  abvbranch 上级机构号 
	 * @return 承保机构集合
	 */
	@ResponseBody
	@RequestMapping("getbyabv.do")
	public List<Dept> getByAbvbranch(String abvbranch){ 
		if(abvbranch!=null&&!"".equals(abvbranch)){
			return chooseService.getByAbvbranch(abvbranch);
		}
		
		return null;
	}
	
	 
	
	/**
	 * 查询指定机构级别的承保机构
	 *    
	 * @param  kind 机构类型 
	 * @return 承保机构集合
	 */
	@ResponseBody
	@RequestMapping("getbykind.do")
	public List<Dept> getBykind(String kind){ 
		if(kind!=null&&!"".equals(kind)){
			return chooseService.getByKind(kind);
		}
		
		return null;
	}  
	 
	/**
	 * 根据代码查询承保机构
	 *    
	 * @param  deptId 机构代码 
	 * @return 承保机构
	 */
	@ResponseBody
	@RequestMapping("dept.do")
	public Dept getDept(String deptId){ 
		Dept dept = new Dept();
		dept.setDeptId(deptId);
		return  deptService.get(dept);
	} 
	
}
