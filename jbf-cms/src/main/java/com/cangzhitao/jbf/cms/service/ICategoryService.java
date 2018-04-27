package com.cangzhitao.jbf.cms.service;

import java.util.List;

import com.cangzhitao.jbf.cms.domain.Category;
import com.cangzhitao.jbf.core.service.base.IBaseService;

public interface ICategoryService extends IBaseService<Category, Long> {

	public Category getDefault(List<Category> categorys);
	
	public List<Category> getCategoryByPostId(Long postid);
	
}
