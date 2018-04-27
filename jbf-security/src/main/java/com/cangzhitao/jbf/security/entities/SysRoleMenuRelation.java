package com.cangzhitao.jbf.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_sys_role_menu")
public class SysRoleMenuRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6092810682607987774L;

	private Long roleid;
	
	private Long menuid;
	
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getMenuid() {
		return menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	
}
