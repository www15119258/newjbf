package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Spage;
import com.cangzhitao.jbf.cms.repository.SpageRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class SpageService extends BaseService<Spage, Long> implements ISpageService {

	@Autowired
	private SpageRepository spageRepository;
	
	@Override
	public BaseRepository<Spage, Long> getRepository() {
		return spageRepository;
	}
	
}
