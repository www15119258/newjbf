package com.cangzhitao.jbf.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_cms_post_category")
public class PostCategoryRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383022022691411931L;

	private Long postid;
	
	private Long categoryid;

	public Long getPostid() {
		return postid;
	}

	public void setPostid(Long postid) {
		this.postid = postid;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	
}
