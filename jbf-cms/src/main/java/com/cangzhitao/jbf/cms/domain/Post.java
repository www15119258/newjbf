package com.cangzhitao.jbf.cms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.cangzhitao.jbf.cms.enmus.PostStatus;
import com.cangzhitao.jbf.cms.enmus.SourceFrom;
import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_post")
public class Post extends BaseEntity implements IContentEntity {

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
	
	@Size(min=0, max=2000, message="图片地址的长度不能超过{max}")
	@Lob
	private String pic;
	
	private SourceFrom sourceFrom = SourceFrom.ORIGINAL;
	
	@NotNull(message="是否开启评论不能为空")
	private Enable commentEnable = Enable.TRUE;
	
	@NotNull(message="是否显示评论不能为空")
	private Enable commentShow = Enable.TRUE;
	
	private String badge;
	
	@NotNull(message="是否置顶不能为空")
	private Boolean isTop = false;
	
	private Integer topLevel;
	
	private Integer viewCount = 0;
	
	private Integer commentCount = 0;
	
	private Integer supportCount = 0;
	
	private Integer collectionCount = 0;
	
	@NotNull(message="状态不能为空")
	private PostStatus status = PostStatus.SAVE;
	
	@NotNull(message="所属分类不能为空")
	private Long category;
	
	@Transient
	private String[] tagclouds;
	
	@Transient
	private List<Tagcloud> tagcloudList;
	
	@Transient
	private Long[] categorys;
	
	@Transient
	private List<Category> categoryList;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date publishDate;
	
	@Transient
	private String creator;
	
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

	public SourceFrom getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(SourceFrom sourceFrom) {
		this.sourceFrom = sourceFrom;
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

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Integer getTopLevel() {
		return topLevel;
	}

	public void setTopLevel(Integer topLevel) {
		this.topLevel = topLevel;
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

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Integer getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(Integer supportCount) {
		this.supportCount = supportCount;
	}

	public String[] getTagclouds() {
		return tagclouds;
	}

	public void setTagclouds(String[] tagclouds) {
		this.tagclouds = tagclouds;
	}

	public Long[] getCategorys() {
		return categorys;
	}

	public void setCategorys(Long[] categorys) {
		this.categorys = categorys;
	}

	public List<Tagcloud> getTagcloudList() {
		return tagcloudList;
	}

	public void setTagcloudList(List<Tagcloud> tagcloudList) {
		this.tagcloudList = tagcloudList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
