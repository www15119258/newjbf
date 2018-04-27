package com.cangzhitao.jbf.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.cms.domain.Comment;
import com.cangzhitao.jbf.cms.repository.CommentRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class CommentService extends BaseService<Comment, Long> implements ICommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public BaseRepository<Comment, Long> getRepository() {
		return commentRepository;
	}
	
}
