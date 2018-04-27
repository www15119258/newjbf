package com.cangzhitao.jbf.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.repository.SysUserRoleRelationRepository;

@Service
public class SysUserRoleRelationService extends BaseService<SysUserRoleRelation, Long> implements ISysUserRoleRelationService {

	@Autowired
	private SysUserRoleRelationRepository sysUserRoleRelationRepository;
	
	@Override
	public BaseRepository<SysUserRoleRelation, Long> getRepository() {
		return sysUserRoleRelationRepository;
	}

}
