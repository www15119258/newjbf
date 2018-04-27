package com.cangzhitao.jbf.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_cms_spage_comment")
public class SpageCommentRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6383022022691411931L;

	private Long spageid;
	
	private Long commentid;

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public Long getSpageid() {
		return spageid;
	}

	public void setSpageid(Long spageid) {
		this.spageid = spageid;
	}

}
