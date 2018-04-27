package com.cangzhitao.jbf.security.util;

import java.util.Set;

import org.apache.shiro.SecurityUtils;

import com.cangzhitao.jbf.core.util.SpringUtil;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.service.ISysMenuService;
import com.cangzhitao.jbf.security.service.ISysModuleService;
import com.cangzhitao.jbf.security.service.ISysRoleService;
import com.cangzhitao.jbf.security.service.ISysUserService;

public class UserManager {

	public static SysUser getCurrentUser() {
		SysUser user = null;
		try {
			user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		} catch (Exception e) {
		}
		return user;
	}
	
	public static SysUser getUser(Long id) {
		ISysUserService sysUserService = SpringUtil.getBean(ISysUserService.class);
		SysUser user = sysUserService.findById(id);
		return user;
	}
	
	public static Long getCurrentUserId() {
		SysUser user = getCurrentUser();
		if(user!=null) {
			return user.getId();
		}
		return null;
	}
	
	public static String getSessionId() {
		return SecurityUtils.getSubject().getSession().getId() + "";
	}
	
	public static Set<String> getPermSet() {
		ISysUserService sysUserService = SpringUtil.getBean(ISysUserService.class);
		return sysUserService.getPermSet(getCurrentUser().getUsername());
	}
	
	public static Set<String> getRoleSetByUsername(String username) {
		ISysUserService sysUserService = SpringUtil.getBean(ISysUserService.class);
		return sysUserService.getRoleSet(username);
	}
	
	public static Set<String> getMenuSetByRoleid(Long roleid) {
		ISysRoleService sysRoleService = SpringUtil.getBean(ISysRoleService.class);
		return sysRoleService.getMenuSet(roleid);
	}
	
	public static Set<String> getRoleSetByMenuid(Long menuid) {
		ISysMenuService sysMenuService = SpringUtil.getBean(ISysMenuService.class);
		return sysMenuService.getRoleSet(menuid);
	}
	
	public static Set<String> getRoleSetByModuleid(Long moduleid) {
		ISysModuleService sysModuleService = SpringUtil.getBean(ISysModuleService.class);
		return sysModuleService.getRoleSet(moduleid);
	}
	
	public static Set<String> getModuleSetByRoleid(Long roleid) {
		ISysRoleService sysRoleService = SpringUtil.getBean(ISysRoleService.class);
		return sysRoleService.getModuleSet(roleid);
	}
	
}
