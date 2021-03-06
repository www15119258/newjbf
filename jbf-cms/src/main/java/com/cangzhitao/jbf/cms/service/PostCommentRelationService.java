package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.PostCommentRelation;
import com.cangzhitao.jbf.cms.repository.PostCommentRelationRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class PostCommentRelationService extends BaseService<PostCommentRelation, Long> implements IPostCommentRelationService {

	@Autowired
	private PostCommentRelationRepository postCommentRelationRepository;
	
	@Override
	public BaseRepository<PostCommentRelation, Long> getRepository() {
		return postCommentRelationRepository;
	}
	
}
