package com.cangzhitao.jbf.cms.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.core.service.base.IBaseService;

public interface IPostService extends IBaseService<Post, Long> {

	public Post addPost(Post post);
	
	public Post updatePost(Post post);
	
	public Page<Post> findPost(Map<String, Object> paramMap, Long category, Pageable pageable);
	
}
