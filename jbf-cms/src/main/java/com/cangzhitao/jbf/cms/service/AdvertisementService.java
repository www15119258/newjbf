package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Advertisement;
import com.cangzhitao.jbf.cms.repository.AdvertisementRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class AdvertisementService extends BaseService<Advertisement, Long> implements IAdvertisementService {

	@Autowired
	private AdvertisementRepository advertisementRepository;
	
	@Override
	public BaseRepository<Advertisement, Long> getRepository() {
		return advertisementRepository;
	}
	
}
