package com.cangzhitao.jbf.cms.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.cms.enmus.CommentStatus;
import com.cangzhitao.jbf.cms.enmus.CommentType;
import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.validation.annotation.Email;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;
import com.cangzhitao.jbf.validation.annotation.URL;

@Entity
@Table(name="t_cms_comment")
public class Comment extends BaseEntity implements ITreeEntity<Comment> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4454347797934764646L;

	@NotNull(message="类型不能为空")
	private CommentType type = CommentType.POST;
	
	@NotNull(message="评论所属不能为空")
	private Long belong;
	
	@Size(min=0, max=20, message="昵称的长度不能超过{max}")
	private String nickname;
	
	@Email(message="非法的Email地址")
	private String email;
	
	@URL(message="非法的主页地址")
	private String homepage;
	
	private String ip;
	
	@NotEmpty(message="内容不能为空")
	@Size(min=5, max=200, message="内容的长度必须在{min}和{max}之间")
	private String content;
	
	private Long parent;
	
	@Transient
	private List<Comment> children;
	
	@Transient
	private String parentName;
	
	@NotNull(message="状态不能为空")
	private CommentStatus status = CommentStatus.SAVE;
	
	@Transient
	private Comment parentComment;
	
	@Transient
	private IContentEntity belongContent;
	
	public CommentType getType() {
		return type;
	}

	public void setType(CommentType type) {
		this.type = type;
	}

	public Long getBelong() {
		return belong;
	}

	public void setBelong(Long belong) {
		this.belong = belong;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<Comment> getChildren() {
		return children;
	}

	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public CommentStatus getStatus() {
		return status;
	}

	public void setStatus(CommentStatus status) {
		this.status = status;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public IContentEntity getBelongContent() {
		return belongContent;
	}

	public void setBelongContent(IContentEntity belongContent) {
		this.belongContent = belongContent;
	}
	
}
