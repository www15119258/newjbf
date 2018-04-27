package com.cangzhitao.jbf.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.config.service.ISysDictService;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.core.enums.Visible;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysMenu;
import com.cangzhitao.jbf.security.entities.SysModule;
import com.cangzhitao.jbf.security.entities.SysModulePermRelation;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.entities.SysRole;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.enums.SysUserStatus;
import com.cangzhitao.jbf.security.service.ISysMenuService;
import com.cangzhitao.jbf.security.service.ISysModulePermRelationService;
import com.cangzhitao.jbf.security.service.ISysModuleService;
import com.cangzhitao.jbf.security.service.ISysPermService;
import com.cangzhitao.jbf.security.service.ISysRoleMenuRelationService;
import com.cangzhitao.jbf.security.service.ISysRoleModuleRelationService;
import com.cangzhitao.jbf.security.service.ISysRoleService;
import com.cangzhitao.jbf.security.service.ISysUserRoleRelationService;
import com.cangzhitao.jbf.security.service.ISysUserService;

@Controller 
@RequestMapping("/${sys.path}/security") 
public class InitDataController {
	
	@Autowired
	private ISysUserService sysUserSerivce;
	
	@Autowired
	private ISysRoleService sysRoleService;
	
	@Autowired
	private ISysPermService sysPermService;
	
	@Autowired
	private ISysUserRoleRelationService sysUserRoleRelationService;
	
	@Autowired
	private ISysModuleService sysModuleService;
	
	@Autowired
	private ISysRoleModuleRelationService sysRoleModuleRelationService;
	
	@Autowired
	private ISysModulePermRelationService sysModulePermRelationService;
	
	@Autowired
	private ISysDictService sysDictService;
	
	@Autowired
	private ISysMenuService sysMenuService;
	
	@Autowired
	private ISysRoleMenuRelationService sysRoleMenuRelationService;
	
	@RequestMapping(value={"init"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean init() {
		ResultBean resultBean = new ResultBean();
		SysUser adminUser = new SysUser().setNull().setProperty("deleteFlag", false).setProperty("username", "admin");
		adminUser = sysUserSerivce.findOne(adminUser);
		if(adminUser==null) {
			adminUser = new SysUser();
			adminUser.setUsername("admin");
			adminUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
			adminUser.setNickname("系统管理员");
			adminUser.setStatus(SysUserStatus.NORMAL);
			adminUser.setCreateInfo();
			sysUserSerivce.save(adminUser);
		}
		if(adminUser.getStatus() != SysUserStatus.NORMAL) {
			adminUser.setStatus(SysUserStatus.NORMAL);
			adminUser.setUpdateInfo();
			sysUserSerivce.save(adminUser);
		}
		SysDict sysDict = new SysDict().setNull().setProperty("deleteFlag", false).setProperty("type", "sys_role_type").setProperty("value", "0");
		sysDict = sysDictService.findOne(sysDict);
		if(sysDict==null) {
			sysDict = new SysDict();
			sysDict.setType("sys_role_type");
			sysDict.setValue("0");
			sysDict.setLabel("角色");
			sysDict.setDescription("角色");
			sysDict.setVisible(Visible.TRUE);
			sysDict.setCreateInfo();
			sysDictService.save(sysDict);
		} else if(sysDict.getVisible()!=Visible.TRUE) {
			sysDict.setVisible(Visible.TRUE);
			sysDict.setUpdateInfo();
			sysDictService.save(sysDict);
		}
		sysDict = new SysDict().setNull().setProperty("deleteFlag", false).setProperty("type", "cms_advertisement_type").setProperty("value", "default");
		sysDict = sysDictService.findOne(sysDict);
		if(sysDict==null) {
			sysDict = new SysDict();
			sysDict.setType("cms_advertisement_type");
			sysDict.setValue("default");
			sysDict.setLabel("default");
			sysDict.setDescription("默认广告类型");
			sysDict.setVisible(Visible.TRUE);
			sysDict.setCreateInfo();
			sysDictService.save(sysDict);
		} else if(sysDict.getVisible()!=Visible.TRUE) {
			sysDict.setVisible(Visible.TRUE);
			sysDict.setUpdateInfo();
			sysDictService.save(sysDict);
		}
		sysDict = new SysDict().setNull().setProperty("deleteFlag", false).setProperty("type", "cms_aggregation_type").setProperty("value", "default");
		sysDict = sysDictService.findOne(sysDict);
		if(sysDict==null) {
			sysDict = new SysDict();
			sysDict.setType("cms_aggregation_type");
			sysDict.setValue("default");
			sysDict.setLabel("default");
			sysDict.setDescription("默认文章集类型");
			sysDict.setVisible(Visible.TRUE);
			sysDict.setCreateInfo();
			sysDictService.save(sysDict);
		} else if(sysDict.getVisible()!=Visible.TRUE) {
			sysDict.setVisible(Visible.TRUE);
			sysDict.setUpdateInfo();
			sysDictService.save(sysDict);
		}
		sysDict = new SysDict().setNull().setProperty("deleteFlag", false).setProperty("type", "sys_menu_type").setProperty("value", "left");
		sysDict = sysDictService.findOne(sysDict);
		if(sysDict==null) {
			sysDict = new SysDict();
			sysDict.setType("sys_menu_type");
			sysDict.setValue("left");
			sysDict.setLabel("系统左导航");
			sysDict.setDescription("系统左导航");
			sysDict.setVisible(Visible.TRUE);
			sysDict.setCreateInfo();
			sysDictService.save(sysDict);
		} else if(sysDict.getVisible()!=Visible.TRUE) {
			sysDict.setVisible(Visible.TRUE);
			sysDict.setUpdateInfo();
			sysDictService.save(sysDict);
		}
		SysRole adminRole = new SysRole().setNull().setProperty("deleteFlag", false).setProperty("rolename", "admin");
		adminRole = sysRoleService.findOne(adminRole);
		if(adminRole==null) {
			adminRole = new SysRole();
			adminRole.setRolename("admin");
			adminRole.setNickname("系统管理员");
			adminRole.setStatus(Enable.TRUE);
			adminRole.setType("0");
			adminRole.setCreateInfo();
			sysRoleService.save(adminRole);
		}
		if(adminRole.getStatus() != Enable.TRUE) {
			adminRole.setStatus(Enable.TRUE);
			adminRole.setUpdateInfo();
			sysRoleService.save(adminRole);
		}
		SysUserRoleRelation adminUserRoleRelation = new SysUserRoleRelation().setNull().setProperty("userid", adminUser.getId()).setProperty("roleid", adminRole.getId());
		adminUserRoleRelation = sysUserRoleRelationService.findOne(adminUserRoleRelation);
		if(adminUserRoleRelation==null) {
			adminUserRoleRelation = new SysUserRoleRelation();
			adminUserRoleRelation.setUserid(adminUser.getId());
			adminUserRoleRelation.setRoleid(adminRole.getId());
			adminUserRoleRelation.setCreateInfo();
			sysUserRoleRelationService.save(adminUserRoleRelation);
		}
		SysPerm sysPerm = null;
		List<SysPerm> perms = new ArrayList<SysPerm>();
		
		String permStr = null;
		String permName = null;
		
		permStr = "jbf.config.dict.view";
		permName = "字典查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.dict.add";
		permName = "字典新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.dict.edit";
		permName = "字典编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.dict.delete";
		permName = "字典删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.option.view";
		permName = "参数查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.option.add";
		permName = "参数新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.option.edit";
		permName = "参数编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.config.option.delete";
		permName = "参数删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.user.view";
		permName = "用户查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.user.add";
		permName = "用户新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.user.edit";
		permName = "用户编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.user.delete";
		permName = "用户删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.user.assignRoles";
		permName = "用户分配角色";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.view";
		permName = "角色查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.add";
		permName = "角色新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.edit";
		permName = "角色编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.delete";
		permName = "角色删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.assignMenus";
		permName = "角色分配目录";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.assignModules";
		permName = "角色分配模块";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.addUser";
		permName = "角色添加用户";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.role.removeUser";
		permName = "角色移除用户";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.menu.view";
		permName = "目录查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.menu.add";
		permName = "目录新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.menu.edit";
		permName = "目录编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.menu.delete";
		permName = "目录删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.menu.assignRoles";
		permName = "目录分配角色";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.view";
		permName = "模块查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.add";
		permName = "模块新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.edit";
		permName = "模块编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.delete";
		permName = "模块删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.assignRoles";
		permName = "模块分配角色";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.addPerm";
		permName = "模块添加权限";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.module.removePerm";
		permName = "模块移除权限";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.perm.view";
		permName = "权限查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.perm.add";
		permName = "权限新增";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.perm.edit";
		permName = "权限编辑";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.security.perm.delete";
		permName = "权限删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.advertisement.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("广告查询");
			sysPerm.setPerm("jbf.cms.advertisement.view");
			sysPerm.setDescription("广告查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.advertisement.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("广告新增");
			sysPerm.setPerm("jbf.cms.advertisement.add");
			sysPerm.setDescription("广告新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.advertisement.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("广告编辑");
			sysPerm.setPerm("jbf.cms.advertisement.edit");
			sysPerm.setDescription("广告编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.advertisement.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("广告删除");
			sysPerm.setPerm("jbf.cms.advertisement.delete");
			sysPerm.setDescription("广告删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.aggregation.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章集查询");
			sysPerm.setPerm("jbf.cms.aggregation.view");
			sysPerm.setDescription("文章集查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.aggregation.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章集新增");
			sysPerm.setPerm("jbf.cms.aggregation.add");
			sysPerm.setDescription("文章集新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.aggregation.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章集编辑");
			sysPerm.setPerm("jbf.cms.aggregation.edit");
			sysPerm.setDescription("文章集编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.aggregation.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章集删除");
			sysPerm.setPerm("jbf.cms.aggregation.delete");
			sysPerm.setDescription("文章集删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.category.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("分类查询");
			sysPerm.setPerm("jbf.cms.category.view");
			sysPerm.setDescription("分类查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.category.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("分类新增");
			sysPerm.setPerm("jbf.cms.category.add");
			sysPerm.setDescription("分类新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.category.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("分类编辑");
			sysPerm.setPerm("jbf.cms.category.edit");
			sysPerm.setDescription("分类编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.category.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("分类删除");
			sysPerm.setPerm("jbf.cms.category.delete");
			sysPerm.setDescription("分类删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.comment.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("评论查询");
			sysPerm.setPerm("jbf.cms.comment.view");
			sysPerm.setDescription("评论查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.comment.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("评论新增");
			sysPerm.setPerm("jbf.cms.comment.add");
			sysPerm.setDescription("评论新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.comment.reply");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("评论回复");
			sysPerm.setPerm("jbf.cms.comment.reply");
			sysPerm.setDescription("评论回复");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.comment.verify");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("评论审核");
			sysPerm.setPerm("jbf.cms.comment.verify");
			sysPerm.setDescription("评论审核");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.comment.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("评论删除");
			sysPerm.setPerm("jbf.cms.comment.delete");
			sysPerm.setDescription("评论删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.friendLink.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("友情链接查询");
			sysPerm.setPerm("jbf.cms.friendLink.view");
			sysPerm.setDescription("友情链接查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.friendLink.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("友情链接新增");
			sysPerm.setPerm("jbf.cms.friendLink.add");
			sysPerm.setDescription("友情链接新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.friendLink.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("友情链接编辑");
			sysPerm.setPerm("jbf.cms.friendLink.edit");
			sysPerm.setDescription("友情链接编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.friendLink.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("友情链接删除");
			sysPerm.setPerm("jbf.cms.friendLink.delete");
			sysPerm.setDescription("友情链接删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.post.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章查询");
			sysPerm.setPerm("jbf.cms.post.view");
			sysPerm.setDescription("文章查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.post.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章新增");
			sysPerm.setPerm("jbf.cms.post.add");
			sysPerm.setDescription("文章新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.post.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章编辑");
			sysPerm.setPerm("jbf.cms.post.edit");
			sysPerm.setDescription("文章编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.post.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("文章删除");
			sysPerm.setPerm("jbf.cms.post.delete");
			sysPerm.setDescription("文章删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.spage.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("页面查询");
			sysPerm.setPerm("jbf.cms.spage.view");
			sysPerm.setDescription("页面查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.spage.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("页面新增");
			sysPerm.setPerm("jbf.cms.spage.add");
			sysPerm.setDescription("页面新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.spage.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("页面编辑");
			sysPerm.setPerm("jbf.cms.spage.edit");
			sysPerm.setDescription("页面编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.spage.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("页面删除");
			sysPerm.setPerm("jbf.cms.spage.delete");
			sysPerm.setDescription("页面删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.tagcloud.view");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("标签查询");
			sysPerm.setPerm("jbf.cms.tagcloud.view");
			sysPerm.setDescription("标签查询");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.tagcloud.add");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("标签新增");
			sysPerm.setPerm("jbf.cms.tagcloud.add");
			sysPerm.setDescription("标签新增");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.tagcloud.edit");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("标签编辑");
			sysPerm.setPerm("jbf.cms.tagcloud.edit");
			sysPerm.setDescription("标签编辑");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPerm = findPerm("jbf.cms.tagcloud.delete");
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName("标签删除");
			sysPerm.setPerm("jbf.cms.tagcloud.delete");
			sysPerm.setDescription("标签删除");
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.statistics.visitLog.view";
		permName = "访问记录查询";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.statistics.visitLog.delete";
		permName = "访问记录删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.statistics.visitLog.delete";
		permName = "访问记录删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.view";
		permName = "附件查看";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.newFolder";
		permName = "附件-新建文件夹";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.rename";
		permName = "附件-重命名";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.delete";
		permName = "附件-删除";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.copy";
		permName = "附件-复制";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.uploadFromNet";
		permName = "附件-从网络上传";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		permStr = "jbf.filemanage.file.uploadFromLocal";
		permName = "附件-从本地上传";
		sysPerm = findPerm(permStr);
		if(sysPerm==null) {
			sysPerm = new SysPerm();
			sysPerm.setName(permName);
			sysPerm.setPerm(permStr);
			sysPerm.setDescription(permName);
			sysPerm.setCreateInfo();
			perms.add(sysPerm);
		} else if(sysPerm.getStatus()!=Enable.TRUE){
			sysPerm.setStatus(Enable.TRUE);
			sysPerm.setUpdateInfo();
			perms.add(sysPerm);
		}
		
		sysPermService.save(perms);
		
		SysModule sysModule = null;
		String moduleName = null;
		String parentModuleName = null;
		
		moduleName = "系统设置";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "系统设置";
		moduleName = "字典设置";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "字典设置";
		moduleName = "字典查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "字典设置";
		moduleName = "字典新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "字典设置";
		moduleName = "字典编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "字典设置";
		moduleName = "字典删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "系统设置";
		moduleName = "参数设置";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "参数设置";
		moduleName = "参数查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "参数设置";
		moduleName = "参数新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "参数设置";
		moduleName = "参数编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "参数设置";
		moduleName = "参数删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		moduleName = "用户权限管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户权限管理";
		moduleName = "用户管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户管理";
		moduleName = "用户查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户管理";
		moduleName = "用户新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户管理";
		moduleName = "用户编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户管理";
		moduleName = "用户删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户管理";
		moduleName = "用户分配角色";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户权限管理";
		moduleName = "角色管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色分配目录";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色分配模块";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色添加用户";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "角色管理";
		moduleName = "角色移除用户";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户权限管理";
		moduleName = "目录管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "目录管理";
		moduleName = "目录查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "目录管理";
		moduleName = "目录新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "目录管理";
		moduleName = "目录编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "目录管理";
		moduleName = "目录删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "目录管理";
		moduleName = "目录分配角色";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户权限管理";
		moduleName = "模块管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块分配角色";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块添加权限";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "模块管理";
		moduleName = "模块移除权限";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "用户权限管理";
		moduleName = "权限管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "权限管理";
		moduleName = "权限查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "权限管理";
		moduleName = "权限新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "权限管理";
		moduleName = "权限编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "权限管理";
		moduleName = "权限删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		moduleName = "文章内容管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "分类管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "文章管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "页面管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "评论管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "标签管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "友情链接管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "广告管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章内容管理";
		moduleName = "文章集管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "分类管理";
		moduleName = "分类查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "分类管理";
		moduleName = "分类新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "分类管理";
		moduleName = "分类编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "分类管理";
		moduleName = "分类删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}

		parentModuleName = "文章管理";
		moduleName = "文章查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章管理";
		moduleName = "文章新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章管理";
		moduleName = "文章编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章管理";
		moduleName = "文章删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "页面管理";
		moduleName = "页面查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "页面管理";
		moduleName = "页面新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "页面管理";
		moduleName = "页面编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "页面管理";
		moduleName = "页面删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "评论管理";
		moduleName = "评论查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "评论管理";
		moduleName = "评论新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "评论管理";
		moduleName = "评论回复";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "评论管理";
		moduleName = "评论审核";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "评论管理";
		moduleName = "评论删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "标签管理";
		moduleName = "标签查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "标签管理";
		moduleName = "标签新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "标签管理";
		moduleName = "标签编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "标签管理";
		moduleName = "标签删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "友情链接管理";
		moduleName = "友情链接查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "友情链接管理";
		moduleName = "友情链接新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "友情链接管理";
		moduleName = "友情链接编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "友情链接管理";
		moduleName = "友情链接删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "广告管理";
		moduleName = "广告查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "广告管理";
		moduleName = "广告新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "广告管理";
		moduleName = "广告编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "广告管理";
		moduleName = "广告删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章集管理";
		moduleName = "文章集查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章集管理";
		moduleName = "文章集新增";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章集管理";
		moduleName = "文章集编辑";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "文章集管理";
		moduleName = "文章集删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		moduleName = "站点统计";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "站点统计";
		moduleName = "访问记录";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "访问记录";
		moduleName = "访问记录查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "访问记录";
		moduleName = "访问记录删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		moduleName = "附件管理";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件查询";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-新建文件夹";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-重命名";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-删除";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-复制";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-从网络上传";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		parentModuleName = "附件管理";
		moduleName = "附件-从本地上传";
		sysModule = findModule(moduleName);
		if(sysModule==null) {
			sysModule = new SysModule();
			sysModule.setName(moduleName);
			sysModule.setDescription(moduleName);
			sysModule.setParent(findModule(parentModuleName).getId());
			sysModule.setCreateInfo();
			sysModuleService.save(sysModule);
		} else if(sysModule.getStatus()!=Enable.TRUE) {
			sysModule.setStatus(Enable.TRUE);
			sysModule.setUpdateInfo();
			sysModuleService.save(sysModule);
		}
		
		SysModulePermRelation modulePerm = null;
		SysModule module = null;
		SysPerm perm = null;
		
		module = findModule("字典查询");
		perm = findPerm("jbf.config.dict.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("字典新增");
		perm = findPerm("jbf.config.dict.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("字典编辑");
		perm = findPerm("jbf.config.dict.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("字典删除");
		perm = findPerm("jbf.config.dict.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("参数查询");
		perm = findPerm("jbf.config.option.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("参数新增");
		perm = findPerm("jbf.config.option.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("参数编辑");
		perm = findPerm("jbf.config.option.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("参数删除");
		perm = findPerm("jbf.config.option.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("用户查询");
		perm = findPerm("jbf.security.user.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("用户新增");
		perm = findPerm("jbf.security.user.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("用户编辑");
		perm = findPerm("jbf.security.user.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("用户删除");
		perm = findPerm("jbf.security.user.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("用户分配角色");
		perm = findPerm("jbf.security.user.assignRoles");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色查询");
		perm = findPerm("jbf.security.role.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色新增");
		perm = findPerm("jbf.security.role.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色编辑");
		perm = findPerm("jbf.security.role.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色删除");
		perm = findPerm("jbf.security.role.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色分配目录");
		perm = findPerm("jbf.security.role.assignMenus");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色分配模块");
		perm = findPerm("jbf.security.role.assignModules");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色添加用户");
		perm = findPerm("jbf.security.role.addUser");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("角色移除用户");
		perm = findPerm("jbf.security.role.removeUser");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("目录查询");
		perm = findPerm("jbf.security.menu.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("目录新增");
		perm = findPerm("jbf.security.menu.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("目录编辑");
		perm = findPerm("jbf.security.menu.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("目录删除");
		perm = findPerm("jbf.security.menu.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("目录分配角色");
		perm = findPerm("jbf.security.menu.assignRoles");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块查询");
		perm = findPerm("jbf.security.module.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块新增");
		perm = findPerm("jbf.security.module.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块编辑");
		perm = findPerm("jbf.security.module.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块删除");
		perm = findPerm("jbf.security.module.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块分配角色");
		perm = findPerm("jbf.security.module.assignRoles");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块添加权限");
		perm = findPerm("jbf.security.module.addPerm");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("模块移除权限");
		perm = findPerm("jbf.security.module.removePerm");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("权限查询");
		perm = findPerm("jbf.security.perm.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("权限新增");
		perm = findPerm("jbf.security.perm.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("权限编辑");
		perm = findPerm("jbf.security.perm.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("权限删除");
		perm = findPerm("jbf.security.perm.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("分类查询");
		perm = findPerm("jbf.cms.category.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("分类新增");
		perm = findPerm("jbf.cms.category.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("分类编辑");
		perm = findPerm("jbf.cms.category.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("分类删除");
		perm = findPerm("jbf.cms.category.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章查询");
		perm = findPerm("jbf.cms.post.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章新增");
		perm = findPerm("jbf.cms.post.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章编辑");
		perm = findPerm("jbf.cms.post.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章删除");
		perm = findPerm("jbf.cms.post.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("页面查询");
		perm = findPerm("jbf.cms.spage.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("页面新增");
		perm = findPerm("jbf.cms.spage.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("页面编辑");
		perm = findPerm("jbf.cms.spage.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("页面删除");
		perm = findPerm("jbf.cms.spage.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("评论查询");
		perm = findPerm("jbf.cms.comment.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("评论新增");
		perm = findPerm("jbf.cms.comment.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("评论回复");
		perm = findPerm("jbf.cms.comment.reply");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("评论审核");
		perm = findPerm("jbf.cms.comment.verify");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("评论删除");
		perm = findPerm("jbf.cms.comment.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("标签查询");
		perm = findPerm("jbf.cms.tagcloud.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("标签新增");
		perm = findPerm("jbf.cms.tagcloud.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("标签编辑");
		perm = findPerm("jbf.cms.tagcloud.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("标签删除");
		perm = findPerm("jbf.cms.tagcloud.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("友情链接查询");
		perm = findPerm("jbf.cms.friendLink.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("友情链接新增");
		perm = findPerm("jbf.cms.friendLink.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("友情链接编辑");
		perm = findPerm("jbf.cms.friendLink.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("友情链接删除");
		perm = findPerm("jbf.cms.friendLink.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("广告查询");
		perm = findPerm("jbf.cms.advertisement.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("广告新增");
		perm = findPerm("jbf.cms.advertisement.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("广告编辑");
		perm = findPerm("jbf.cms.advertisement.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("广告删除");
		perm = findPerm("jbf.cms.advertisement.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章集查询");
		perm = findPerm("jbf.cms.aggregation.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章集新增");
		perm = findPerm("jbf.cms.aggregation.add");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章集编辑");
		perm = findPerm("jbf.cms.aggregation.edit");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("文章集删除");
		perm = findPerm("jbf.cms.aggregation.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("访问记录查询");
		perm = findPerm("jbf.statistics.visitLog.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("访问记录删除");
		perm = findPerm("jbf.statistics.visitLog.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件查询");
		perm = findPerm("jbf.filemanage.file.view");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-新建文件夹");
		perm = findPerm("jbf.filemanage.file.newFolder");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-重命名");
		perm = findPerm("jbf.filemanage.file.rename");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-删除");
		perm = findPerm("jbf.filemanage.file.delete");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-复制");
		perm = findPerm("jbf.filemanage.file.copy");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-从网络上传");
		perm = findPerm("jbf.filemanage.file.uploadFromNet");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		module = findModule("附件-从本地上传");
		perm = findPerm("jbf.filemanage.file.uploadFromLocal");
		modulePerm = new SysModulePermRelation().setProperty("moduleid", module.getId()).setProperty("permid", perm.getId()).setProperty("deleteFlag", false);
		modulePerm = sysModulePermRelationService.findOne(modulePerm);
		if(modulePerm==null) {
			modulePerm = new SysModulePermRelation();
			modulePerm.setModuleid(module.getId());
			modulePerm.setPermid(perm.getId());
			modulePerm.setCreateInfo();
			sysModulePermRelationService.save(modulePerm);
		}
		
		sysModule = new SysModule().setNull().setProperty("deleteFlag", false);
		List<SysModule> modules = sysModuleService.findAll(sysModule);
		for(int i=0;i<modules.size();i++) {
			SysRoleModuleRelation roleModule = new SysRoleModuleRelation().setNull().setProperty("roleid", adminRole.getId()).setProperty("moduleid", modules.get(i).getId()).setProperty("deleteFlag", false);
			roleModule = sysRoleModuleRelationService.findOne(roleModule);
			if(roleModule==null) {
				roleModule = new SysRoleModuleRelation();
				roleModule.setRoleid(adminRole.getId());
				roleModule.setModuleid(modules.get(i).getId());
				roleModule.setCreateInfo();
				sysRoleModuleRelationService.save(roleModule);
			}
		}
		
		SysMenu sysMenu = null;
		String parentMenuName = null;
		String menuName = null;
		
		menuName = "仪表盘";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/dashboard");
			sysMenu.setType("left");
			sysMenu.setIcon("dashboard");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		menuName = "系统设置";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setType("left");
			sysMenu.setIcon("th-large");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "系统设置";
		menuName = "字典设置";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/config/dict");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "系统设置";
		menuName = "参数设置";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/config/option");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		menuName = "用户权限管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setType("left");
			sysMenu.setIcon("group");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "用户权限管理";
		menuName = "用户管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/security/user");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "用户权限管理";
		menuName = "角色管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/security/role");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "用户权限管理";
		menuName = "目录管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/security/menu");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "用户权限管理";
		menuName = "模块管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/security/module");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "用户权限管理";
		menuName = "权限管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/security/perm");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		menuName = "文章内容管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setType("left");
			sysMenu.setIcon("list-alt");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "分类管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/category");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "文章管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/post");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "页面管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/spage");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "评论管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/comment");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "标签管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/tagcloud");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "友情链接管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/friendLink");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "广告管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/advertisement");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "文章内容管理";
		menuName = "文章集管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/cms/aggregation");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		menuName = "附件管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setType("left");
			sysMenu.setIcon("file");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "附件管理";
		menuName = "文件管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/filemanage/file");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "附件管理";
		menuName = "图片管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/filemanage/imagefile");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "附件管理";
		menuName = "视频管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/filemanage/moviefile");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "附件管理";
		menuName = "音频管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/filemanage/soundfile");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "附件管理";
		menuName = "压缩包管理";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/filemanage/zipfile");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		menuName = "站点统计";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setType("left");
			sysMenu.setIcon("bar-chart-o");
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		parentMenuName = "站点统计";
		menuName = "访问记录";
		sysMenu = findMenu(menuName, "left");
		if(sysMenu==null) {
			sysMenu = new SysMenu();
			sysMenu.setName(menuName);
			sysMenu.setUrl("admin/statistics/visitlog");
			sysMenu.setType("left");
			sysMenu.setParent(findMenu(parentMenuName,"left").getId());
			sysMenu.setCreateInfo();
			sysMenuService.save(sysMenu);
		} else if(sysMenu.getVisible()!=Visible.TRUE) {
			sysMenu.setVisible(Visible.TRUE);
			sysMenu.setUpdateInfo();
			sysMenuService.save(sysMenu);
		}
		
		sysMenu = new SysMenu().setNull().setProperty("deleteFlag", false);
		List<SysMenu> menus = sysMenuService.findAll(sysMenu);
		for(int i=0;i<menus.size();i++) {
			SysRoleMenuRelation roleMenu = new SysRoleMenuRelation().setNull().setProperty("roleid", adminRole.getId()).setProperty("menuid", menus.get(i).getId());
			roleMenu = sysRoleMenuRelationService.findOne(roleMenu);
			if(roleMenu==null) {
				roleMenu = new SysRoleMenuRelation();
				roleMenu.setRoleid(adminRole.getId());
				roleMenu.setMenuid(menus.get(i).getId());
				roleMenu.setCreateInfo();
				sysRoleMenuRelationService.save(roleMenu);
			}
		}
		
		resultBean.setStatus(ResultBean.SUCCESS);
		return resultBean;
	}
	
	private SysPerm findPerm(String perm) {
		SysPerm example = new SysPerm().setNull().setProperty("deleteFlag", false).setProperty("perm", perm);
		example = sysPermService.findOne(example);
		return example;
	}
	
	private SysModule findModule(String module) {
		SysModule example = new SysModule().setNull().setProperty("deleteFlag", false).setProperty("name", module);
		example = sysModuleService.findOne(example);
		return example;
	}
	
	private SysMenu findMenu(String menu, String type) {
		SysMenu example = new SysMenu().setNull().setProperty("deleteFlag", false).setProperty("name", menu).setProperty("type", type);
		example = sysMenuService.findOne(example);
		return example;
	}
	
}
