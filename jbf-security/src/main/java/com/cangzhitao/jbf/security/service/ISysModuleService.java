package com.cangzhitao.jbf.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.jbf.core.service.base.IBaseService;
import com.cangzhitao.jbf.security.entities.SysModule;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;

public interface ISysModuleService extends IBaseService<SysModule, Long>{

	public Set<String> getRoleSet(Long menuid);
	
	public List<SysRoleModuleRelation> assignRoles(Long module, String type, Long[] roles);
	
	public Page<SysPerm> getPermsById(Long moduleid, Pageable pageable);
	
	public Page<SysPerm> getUnSelectPermsById(Long moduleid, Map<String, Object> paramMap, Pageable pageable);
	
}
