package com.cangzhitao.jbf.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_sys_user_role")
public class SysUserRoleRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6857337751444051282L;

	private Long userid;
	
	private Long roleid;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	
}
