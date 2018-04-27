package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.PostCategoryRelation;
import com.cangzhitao.jbf.cms.repository.PostCategoryRelationRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class PostCategoryRelationService extends BaseService<PostCategoryRelation, Long> implements IPostCategoryRelationService {

	@Autowired
	private PostCategoryRelationRepository postCategroyRelationRepository;
	
	@Override
	public BaseRepository<PostCategoryRelation, Long> getRepository() {
		return postCategroyRelationRepository;
	}
	
}
