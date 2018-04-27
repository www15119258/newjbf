package com.cangzhitao.jbf.security.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;

@Entity
@Table(name="t_sys_module_perm")
public class SysModulePermRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700607073658384040L;

	private Long moduleid;
	
	private Long permid;
	
	public Long getModuleid() {
		return moduleid;
	}

	public void setModuleid(Long moduleid) {
		this.moduleid = moduleid;
	}

	public Long getPermid() {
		return permid;
	}

	public void setPermid(Long permid) {
		this.permid = permid;
	}
	
}
