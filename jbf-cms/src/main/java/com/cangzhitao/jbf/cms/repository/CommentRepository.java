package com.cangzhitao.jbf.cms.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.cms.domain.Comment;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> {

}
