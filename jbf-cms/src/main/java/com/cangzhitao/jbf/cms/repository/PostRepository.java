package com.cangzhitao.jbf.cms.repository;

import org.springframework.stereotype.Repository;

import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {

}
