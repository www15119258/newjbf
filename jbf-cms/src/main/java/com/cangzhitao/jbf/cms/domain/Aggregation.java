package com.cangzhitao.jbf.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.cms.enmus.AggregationType;
import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.core.enums.LinkTarget;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_aggregation")
public class Aggregation extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5450529496729462157L;

	@Size(min=0, max=200, message="名称的长度不能超过{max}")
	private String name;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	@Size(min=0, max=200, message="类型的长度不能超过{max}")
	private String type = "default";
	
	@Size(min=0, max=2000, message="Logo图片地址的长度不能超过{max}")
	@Lob
	private String logo;
	
	@Size(min=0, max=2000, message="链接地址的长度不能超过{max}")
	@Lob
	private String link;
	
	@NotNull(message="链接打开类型不能为空")
	private LinkTarget target = LinkTarget._blank;
	
	@NotNull(message="状态不能为空")
	private Enable status = Enable.TRUE;
	
	@NotNull(message="文章集类型不能为空")
	private AggregationType aggregationType;
	
	/**
	 * 关联的文章、页面、或目录
	 */
	private Long refer;
	
	private Integer sort = 100;
	
	@Transient
	private Post post;
	
	@Transient
	private Spage spage;
	
	@Transient
	private Category category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LinkTarget getTarget() {
		return target;
	}

	public void setTarget(LinkTarget target) {
		this.target = target;
	}

	public Enable getStatus() {
		return status;
	}

	public void setStatus(Enable status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public AggregationType getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(AggregationType aggregationType) {
		this.aggregationType = aggregationType;
	}

	public Long getRefer() {
		return refer;
	}

	public void setRefer(Long refer) {
		this.refer = refer;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Spage getSpage() {
		return spage;
	}

	public void setSpage(Spage spage) {
		this.spage = spage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
