package com.cangzhitao.jbf.cms.service;

import java.util.List;

import com.cangzhitao.jbf.cms.domain.Tagcloud;
import com.cangzhitao.jbf.core.service.base.IBaseService;

public interface ITagcloudService extends IBaseService<Tagcloud, Long> {
	
	public Tagcloud saveTagcloud(Tagcloud tagcloud);
	
	public List<Tagcloud> saveTagclouds(Iterable<Tagcloud> tagclouds);
	
	public List<Tagcloud> getTagcloudByPostId(Long postid);

}
