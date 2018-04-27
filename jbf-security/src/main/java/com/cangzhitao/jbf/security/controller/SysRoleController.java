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
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysRole;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.enums.SysUserStatus;
import com.cangzhitao.jbf.security.service.ISysRoleService;
import com.cangzhitao.jbf.security.service.ISysUserRoleRelationService;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/security/role")  
public class SysRoleController extends BaseController {
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Autowired
	private ISysUserRoleRelationService sysUserRoleRelationService;
	
	@RequiresAuthentication
	@RequestMapping(value={"queryTree/{type}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryTree(@PathVariable String type) {
		SysRole sysRole = new SysRole().setNull().setProperty("type", type).setProperty("deleteFlag", false);
		List<SysRole> sysRoleTree = sysRoleService.findFullTree(sysRole);
		return sysRoleTree;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysRole sysRole) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysRole> page = sysRoleService.findAll(sysRole, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysRole sysRole = sysRoleService.findById(id);
		if(sysRole!=null) {
			resultBean.setData(sysRole);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryMenus/{roleid}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryRoles(@PathVariable Long roleid) {
		if(roleid==null) {
			return new HashSet<String>();
		}
		Set<String> menus = UserManager.getMenuSetByRoleid(roleid);
		return menus;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryModules/{roleid}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryModules(@PathVariable Long roleid) {
		if(roleid==null) {
			return new HashSet<String>();
		}
		Set<String> modules = UserManager.getModuleSetByRoleid(roleid);
		return modules;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryUsers"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryUsers(SysRole role) {
		ResultBean resultBean = new ResultBean();
		if(role==null || role.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("角色不存在！");
			return resultBean;
		}
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysUser> page = sysRoleService.getUsersById(role.getId(), pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryUnSelectUsers"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryUnSelectUsers(Long role, String username, String nickname, String email, SysUserStatus status) {
		ResultBean resultBean = new ResultBean();
		if(role==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("角色不存在！");
			return resultBean;
		}
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(username!=null&&!"".equals(username)) {
			paramMap.put("username", username);
		}
		if(nickname!=null&&!"".equals(nickname)) {
			paramMap.put("nickname", nickname);
		}
		if(email!=null&&!"".equals(email)) {
			paramMap.put("nickname", email);
		}
		if(status!=null) {
			paramMap.put("status", status);
		}
		Page<SysUser> page = sysRoleService.getUnSelectUsersById(role, paramMap, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysRole sysRole) {
		ResultBean resultBean = new ResultBean();
		sysRole.setCreateInfo();
		validate(sysRole.validate());
		SysRole example = new SysRole().setNull().setProperty("rolename", sysRole.getRolename()).setProperty("type", sysRole.getType()).setProperty("deleteFlag", false);
		List<SysRole> roles = sysRoleService.findAll(example);
		if(roles!=null&&roles.size()>0) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("角色名已经存在！");
			return resultBean;
		}
		sysRole = sysRoleService.save(sysRole);
		resultBean.setData(sysRole);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysRole sysRole) {
		ResultBean resultBean = new ResultBean();
		if(sysRole.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysRole old = sysRoleService.findById(sysRole.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setRolename(sysRole.getRolename());
		old.setNickname(sysRole.getNickname());
		old.setDescription(sysRole.getDescription());
		old.setParent(sysRole.getParent());
		old.setSort(sysRole.getSort());
		old.setUpdateInfo();
		validate(old.validate());
		SysRole example = new SysRole().setNull().setProperty("rolename", old.getRolename()).setProperty("type", old.getType()).setProperty("deleteFlag", false);
		List<SysRole> roles = sysRoleService.findAll(example);
		if(roles!=null&&roles.size()>0) {
			for(int i=0;i<roles.size();i++) {
				if(roles.get(i).getId()!=old.getId()) {
					resultBean.setStatus(ResultBean.ERROR);
					resultBean.setMessage("角色名已经存在！");
					return resultBean;
				}
			}
		}
		sysRole = sysRoleService.save(old);
		resultBean.setData(sysRole);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.assignMenus")
	@RequestMapping(value={"assignMenus"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean assignMenus(String role, String type, String menus) {
		ResultBean resultBean = new ResultBean();
		if(role==null||"".equals(role)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("角色不存在！");
			return resultBean;
		}
		List<Long> menuList = new ArrayList<Long>();
		if(menus!=null&&!"".equals(menus)) {
			String[] ms = menus.split(",");
			for(int i=0;i<ms.length;i++) {
				menuList.add(Long.parseLong(ms[i]));
			}
		}
		List<SysRoleMenuRelation> sysRoleMenuRelations = sysRoleService.assignMenus(Long.parseLong(role), type, menuList.toArray(new Long[]{}));
		resultBean.setData(sysRoleMenuRelations);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.assignModules")
	@RequestMapping(value={"assignModules"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean assignModules(String role, String modules) {
		ResultBean resultBean = new ResultBean();
		if(role==null||"".equals(role)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("角色不存在！");
			return resultBean;
		}
		List<Long> moduleList = new ArrayList<Long>();
		if(modules!=null&&!"".equals(modules)) {
			String[] ms = modules.split(",");
			for(int i=0;i<ms.length;i++) {
				moduleList.add(Long.parseLong(ms[i]));
			}
		}
		List<SysRoleModuleRelation> sysRoleModuleRelations = sysRoleService.assignModules(Long.parseLong(role), moduleList.toArray(new Long[]{}));
		resultBean.setData(sysRoleModuleRelations);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.removeUser")
	@RequestMapping(value={"removeUser"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean removeUser(Long role, Long user) {
		ResultBean resultBean = new ResultBean();
		if(role==null||user==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("记录不存在！");
			return resultBean;
		}
		SysUserRoleRelation example = new SysUserRoleRelation();
		example.setUserid(user);
		example.setRoleid(role);
		List<SysUserRoleRelation> list = sysUserRoleRelationService.findAll(example);
		sysUserRoleRelationService.deleteInBatch(list);
		resultBean.setData(list);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.addUser")
	@RequestMapping(value={"addUser"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean addUser(Long role, Long user) {
		ResultBean resultBean = new ResultBean();
		if(role==null||user==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("记录不存在！");
			return resultBean;
		}
		SysUserRoleRelation example = new SysUserRoleRelation();
		example.setUserid(user);
		example.setRoleid(role);
		example.setCreateInfo();
		sysUserRoleRelationService.save(example);
		resultBean.setData(example);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.role.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysRole sysRole = sysRoleService.findById(id);
		if(sysRole!=null) {
			List<SysRole> children = sysRoleService.findTreeAllChildren(sysRole.getId());
			children.add(sysRole);
			for(int i=0;i<children.size();i++) {
				children.get(i).setLogicDelete();
			}
			sysRoleService.save(children);
			resultBean.setData(children);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
