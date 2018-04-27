package com.cangzhitao.jbf.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cangzhitao.jbf.cms.enmus.PostStatus;
import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_spage")
public class Spage extends BaseEntity implements IContentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7611317791667465766L;

	@NotEmpty(message="标题不能为空")
	@Size(min=1, max=200, message="标题的长度必须在{min}和{max}之间")
	private String title;
	
	@Lob
	private String summary;
	
	@Lob
	private String content;
	
	@Size(min=0, max=200, message="固定链接的长度不能超过{max}")
	private String fixedLink;
	
	@Size(min=0, max=200, message="图片地址的长度不能超过{max}")
	private String pic;
	
	@Size(min=0, max=20, message="模板的长度不能超过{max}")
	private String template;
	
	@NotNull(message="是否开启评论不能为空")
	private Enable commentEnable = Enable.TRUE;
	
	@NotNull(message="是否显示评论不能为空")
	private Enable commentShow = Enable.TRUE;
	
	private Integer viewCount = 0;
	
	private Integer commentCount = 0;
	
	private Integer supportCount = 0;
	
	private Integer collectionCount = 0;
	
	@NotNull(message="状态不能为空")
	private PostStatus status = PostStatus.SAVE;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFixedLink() {
		return fixedLink;
	}

	public void setFixedLink(String fixedLink) {
		this.fixedLink = fixedLink;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Enable getCommentEnable() {
		return commentEnable;
	}

	public void setCommentEnable(Enable commentEnable) {
		this.commentEnable = commentEnable;
	}

	public Enable getCommentShow() {
		return commentShow;
	}

	public void setCommentShow(Enable commentShow) {
		this.commentShow = commentShow;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
	}

	public PostStatus getStatus() {
		return status;
	}

	public void setStatus(PostStatus status) {
		this.status = status;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Integer getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(Integer supportCount) {
		this.supportCount = supportCount;
	}
	
}
