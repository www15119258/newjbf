package com.cangzhitao.jbf.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.jbf.core.service.base.IBaseService;
import com.cangzhitao.jbf.security.entities.SysRole;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.entities.SysUser;

public interface ISysRoleService extends IBaseService<SysRole, Long> {
	
	public Set<String> getMenuSet(Long roleid);
	
	public Set<String> getModuleSet(Long roleid);
	
	public List<SysRoleMenuRelation> assignMenus(Long role, String type, Long[] menus);
	
	public List<SysRoleModuleRelation> assignModules(Long role, Long[] modules);
	
	public Page<SysUser> getUsersById(Long roleid, Pageable pageable);
	
	public Page<SysUser> getUnSelectUsersById(Long roleid, Map<String, Object> paramMap, Pageable pageable);
	
}
