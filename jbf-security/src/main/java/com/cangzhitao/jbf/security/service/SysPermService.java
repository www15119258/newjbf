package com.cangzhitao.jbf.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.repository.SysPermRepository;

@Service
public class SysPermService extends BaseService<SysPerm, Long> implements ISysPermService {

	@Autowired
	private SysPermRepository sysPermRepository;
	
	@Override
	public BaseRepository<SysPerm, Long> getRepository() {
		return sysPermRepository;
	}

}
