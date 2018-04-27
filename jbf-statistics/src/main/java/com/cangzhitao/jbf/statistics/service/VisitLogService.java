package com.cangzhitao.jbf.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.statistics.entities.VisitLog;
import com.cangzhitao.jbf.statistics.repository.VisitLogRepository;

@Service
public class VisitLogService extends BaseService<VisitLog, Long> implements IVisitLogService {

	@Autowired
	private VisitLogRepository visitLogRepository;
	
	
	@Override
	public BaseRepository<VisitLog, Long> getRepository() {
		return visitLogRepository;
	}

}
