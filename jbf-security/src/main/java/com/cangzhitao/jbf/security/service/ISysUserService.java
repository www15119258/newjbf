package com.cangzhitao.jbf.security.service;

import java.util.List;
import java.util.Set;

import com.cangzhitao.jbf.core.service.base.IBaseService;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;

public interface ISysUserService extends IBaseService<SysUser, Long> {
	
	public Set<String> getPermSet(String username);
	
	public Set<String> getRoleSet(String username);
	
	public List<SysUserRoleRelation> assignRoles(Long user, String type, Long[] roles);

}
