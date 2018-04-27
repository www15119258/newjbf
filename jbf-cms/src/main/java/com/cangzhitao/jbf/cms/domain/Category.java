package com.cangzhitao.jbf.cms.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_category")
public class Category extends BaseEntity implements ITreeEntity<Category> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7950767851554244687L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=20, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@NotEmpty(message="URL不能为空")
	@Size(min=1, max=20, message="URL的长度必须在{min}和{max}之间")
	private String url;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	private Long parent;
	
	@Transient
	private String parentName;
	
	@Transient
	private List<Category> children;
	
	@Transient
	private Integer deep = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

}
