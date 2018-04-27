package com.cangzhitao.jbf.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.enums.Enable;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_perm")
public class SysPerm extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -72093115440987375L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=20, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@NotEmpty(message="权限不能为空")
	@Size(min=1, max=200, message="权限的长度必须在{min}和{max}之间")
	private String perm;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	@NotNull(message="状态不能为空")
	private Enable status = Enable.TRUE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Enable getStatus() {
		return status;
	}

	public void setStatus(Enable status) {
		this.status = status;
	}
	
}
