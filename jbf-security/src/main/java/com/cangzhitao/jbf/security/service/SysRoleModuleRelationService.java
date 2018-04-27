package com.cangzhitao.jbf.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.repository.SysRoleModuleRelationRepository;

@Service
public class SysRoleModuleRelationService extends BaseService<SysRoleModuleRelation, Long> implements ISysRoleModuleRelationService {

	@Autowired
	private SysRoleModuleRelationRepository sysRoleModuleRelationRepository;
	
	@Override
	public BaseRepository<SysRoleModuleRelation, Long> getRepository() {
		return sysRoleModuleRelationRepository;
	}

}
