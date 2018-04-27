package com.cangzhitao.jbf.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysModulePermRelation;
import com.cangzhitao.jbf.security.repository.SysModulePermRelationRepository;

@Service
public class SysModulePermRelationService extends BaseService<SysModulePermRelation, Long> implements ISysModulePermRelationService {

	@Autowired
	private SysModulePermRelationRepository sysModulePermRelationRepository;
	
	@Override
	public BaseRepository<SysModulePermRelation, Long> getRepository() {
		return sysModulePermRelationRepository;
	}

}
