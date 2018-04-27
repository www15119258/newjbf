package com.cangzhitao.jbf.security.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_module")
public class SysModule extends BaseEntity implements ITreeEntity<SysModule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6615729305786329509L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=20, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	private Long parent;
	
	@NotNull(message="状态不能为空")
	private Enable status = Enable.TRUE;
	
	@Transient
	private String parentName;
	
	@Transient
	private List<SysModule> children;

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

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Enable getStatus() {
		return status;
	}

	public void setStatus(Enable status) {
		this.status = status;
	}

	public List<SysModule> getChildren() {
		return children;
	}

	public void setChildren(List<SysModule> children) {
		this.children = children;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
