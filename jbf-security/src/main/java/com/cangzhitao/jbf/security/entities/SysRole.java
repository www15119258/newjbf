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
@Table(name="t_sys_role")
public class SysRole extends BaseEntity implements ITreeEntity<SysRole> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6907354053004427831L;

	@NotEmpty(message="角色名不能为空")
	@Size(min=2, max=20, message="角色名的长度必须在{min}和{max}之间")
	private String rolename;
	
	@NotEmpty(message="角色昵称不能为空")
	@Size(min=2, max=20, message="角色昵称的长度必须在{min}和{max}之间")
	private String nickname;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	private Long parent;
	
	@NotNull(message="状态不能为空")
	private Enable status = Enable.TRUE;
	
	@NotEmpty(message="类型不能为空")
	private String type = "0";
	
	@Transient
	private String parentName;
	
	@Transient
	private List<SysRole> children;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public List<SysRole> getChildren() {
		return children;
	}

	public void setChildren(List<SysRole> children) {
		this.children = children;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Enable getStatus() {
		return status;
	}

	public void setStatus(Enable status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
