package com.cangzhitao.jbf.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.repository.SysRoleMenuRelationRepository;

@Service
public class SysRoleMenuRelationService extends BaseService<SysRoleMenuRelation, Long> implements ISysRoleMenuRelationService {

	@Autowired
	private SysRoleMenuRelationRepository sysRoleMenuRelationRepository;
	
	@Override
	public BaseRepository<SysRoleMenuRelation, Long> getRepository() {
		return sysRoleMenuRelationRepository;
	}

}
