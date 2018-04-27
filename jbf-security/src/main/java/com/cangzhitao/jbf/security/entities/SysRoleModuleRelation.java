package com.cangzhitao.jbf.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_sys_role_module")
public class SysRoleModuleRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5014522998821683314L;

	private Long roleid;
	
	private Long moduleid;
	
	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

}
