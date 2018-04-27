package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.PostTagcloudRelation;
import com.cangzhitao.jbf.cms.repository.PostTagcloudRelationRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class PostTagcloudRelationService extends BaseService<PostTagcloudRelation, Long> implements IPostTagcloudRelationService {

	@Autowired
	private PostTagcloudRelationRepository postTagcloudRelationRepository;
	
	@Override
	public BaseRepository<PostTagcloudRelation, Long> getRepository() {
		return postTagcloudRelationRepository;
	}
	
}
