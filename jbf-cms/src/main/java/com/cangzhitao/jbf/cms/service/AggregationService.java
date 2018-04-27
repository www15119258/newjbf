package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Aggregation;
import com.cangzhitao.jbf.cms.repository.AggregationRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class AggregationService extends BaseService<Aggregation, Long> implements IAggregationService {

	@Autowired
	private AggregationRepository aggregationRepository;
	
	@Override
	public BaseRepository<Aggregation, Long> getRepository() {
		return aggregationRepository;
	}
	
}
