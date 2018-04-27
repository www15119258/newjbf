package com.cangzhitao.jbf.security.service;

import java.util.List;
import java.util.Set;

import com.cangzhitao.jbf.core.service.base.IBaseService;
import com.cangzhitao.jbf.security.entities.SysMenu;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;

public interface ISysMenuService extends IBaseService<SysMenu, Long> {

	public Set<String> getRoleSet(Long menuid);
	
	public List<SysRoleMenuRelation> assignRoles(Long menu, String type, Long[] roles);
	
}
