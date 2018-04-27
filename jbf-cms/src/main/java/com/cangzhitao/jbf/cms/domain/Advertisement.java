package com.cangzhitao.jbf.cms.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.core.enums.LinkTarget;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_advertisement")
public class Advertisement extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1028688877850735715L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=200, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	@Size(min=0, max=200, message="类型的长度不能超过{max}")
	private String type = "default";
	
	@Size(min=0, max=2000, message="Logo图片地址的长度不能超过{max}")
	@Lob
	private String logo;
	
	@NotEmpty(message="链接地址不能为空")
	@Size(min=0, max=2000, message="链接地址的长度不能超过{max}")
	@Lob
	private String link;
	
	@NotNull(message="链接打开类型不能为空")
	private LinkTarget target = LinkTarget._blank;
	
	@NotNull(message="状态不能为空")
	private Enable status = Enable.TRUE;
	
	private Integer sort = 100;

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

}
