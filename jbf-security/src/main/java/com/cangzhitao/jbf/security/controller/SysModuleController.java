package com.cangzhitao.jbf.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysModule;
import com.cangzhitao.jbf.security.entities.SysModulePermRelation;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.service.ISysModulePermRelationService;
import com.cangzhitao.jbf.security.service.ISysModuleService;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/security/module")
public class SysModuleController extends BaseController {

	@Autowired
	private ISysModuleService sysModuleService;
	
	@Autowired
	private ISysModulePermRelationService sysModulePermRelationService;
	
	@RequiresAuthentication
	@RequestMapping(value={"queryTree"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryTree() {
		SysModule sysModule = new SysModule().setNull().setProperty("deleteFlag", false);
		List<SysModule> sysModuleTree = sysModuleService.findFullTree(sysModule);
		return sysModuleTree;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysModule sysModule) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysModule> page = sysModuleService.findAll(sysModule, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysModule sysModule = sysModuleService.findById(id);
		if(sysModule!=null) {
			resultBean.setData(sysModule);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryRoles/{module}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryRoles(@PathVariable Long module) {
		if(module==null) {
			return new HashSet<String>();
		}
		Set<String> roles = UserManager.getRoleSetByModuleid(module);
		return roles;
	}
	
	@RequiresPermissions(value="jbf.security.module.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysModule sysModule) {
		ResultBean resultBean = new ResultBean();
		sysModule.setCreateInfo();
		validate(sysModule.validate());
		sysModule = sysModuleService.save(sysModule);
		resultBean.setData(sysModule);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysModule sysModule) {
		ResultBean resultBean = new ResultBean();
		if(sysModule.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysModule old = sysModuleService.findById(sysModule.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(sysModule.getName());
		old.setDescription(sysModule.getDescription());
		old.setParent(sysModule.getParent());
		old.setStatus(sysModule.getStatus());
		old.setSort(sysModule.getSort());
		old.setUpdateInfo();
		validate(old.validate());
		sysModule = sysModuleService.save(old);
		resultBean.setData(sysModule);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.assignRoles")
	@RequestMapping(value={"assignRoles"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean assignRoles(Long module, String type, String roles) {
		ResultBean resultBean = new ResultBean();
		if(module==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("模块不存在！");
			return resultBean;
		}
		List<Long> roleList = new ArrayList<Long>();
		if(roles!=null&&!"".equals(roles)) {
			String[] rs = roles.split(",");
			for(int i=0;i<rs.length;i++) {
				roleList.add(Long.parseLong(rs[i]));
			}
		}
		List<SysRoleModuleRelation> sysRoleModuleRelations = sysModuleService.assignRoles(module, type, roleList.toArray(new Long[]{}));
		resultBean.setData(sysRoleModuleRelations);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysModule sysModule = sysModuleService.findById(id);
		if(sysModule!=null) {
			List<SysModule> children = sysModuleService.findTreeAllChildren(sysModule.getId());
			children.add(sysModule);
			for(int i=0;i<children.size();i++) {
				children.get(i).setLogicDelete();
			}
			sysModuleService.save(children);
			resultBean.setData(children);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryPerms"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryPerms(SysModule module) {
		ResultBean resultBean = new ResultBean();
		if(module==null || module.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("模块不存在！");
			return resultBean;
		}
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysPerm> page = sysModuleService.getPermsById(module.getId(), pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryUnSelectPerms"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryUnSelectUsers(Long module, String name, String perm, Enable status) {
		ResultBean resultBean = new ResultBean();
		if(module==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("模块不存在！");
			return resultBean;
		}
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(name!=null&&!"".equals(name)) {
			paramMap.put("name", name);
		}
		if(perm!=null&&!"".equals(perm)) {
			paramMap.put("perm", perm);
		}
		if(status!=null) {
			paramMap.put("status", status);
		}
		Page<SysPerm> page = sysModuleService.getUnSelectPermsById(module, paramMap, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.removePerm")
	@RequestMapping(value={"removePerm"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean removePerm(Long module, Long perm) {
		ResultBean resultBean = new ResultBean();
		if(module==null||perm==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("记录不存在！");
			return resultBean;
		}
		SysModulePermRelation example = new SysModulePermRelation();
		example.setModuleid(module);
		example.setPermid(perm);
		List<SysModulePermRelation> list = sysModulePermRelationService.findAll(example);
		sysModulePermRelationService.deleteInBatch(list);
		resultBean.setData(list);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.addPerm")
	@RequestMapping(value={"addPerm"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean addPerm(Long module, Long perm) {
		ResultBean resultBean = new ResultBean();
		if(module==null||perm==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("记录不存在！");
			return resultBean;
		}
		SysModulePermRelation example = new SysModulePermRelation();
		example.setModuleid(module);
		example.setPermid(perm);
		example.setCreateInfo();
		sysModulePermRelationService.save(example);
		resultBean.setData(example);
		return resultBean;
	}
	
}
