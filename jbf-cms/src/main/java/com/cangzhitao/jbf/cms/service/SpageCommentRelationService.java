package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.SpageCommentRelation;
import com.cangzhitao.jbf.cms.repository.PageCommentRelationRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class SpageCommentRelationService extends BaseService<SpageCommentRelation, Long> implements ISpageCommentRelationService {

	@Autowired
	private PageCommentRelationRepository pageCommentRelationRepository;
	
	@Override
	public BaseRepository<SpageCommentRelation, Long> getRepository() {
		return pageCommentRelationRepository;
	}
	
}
