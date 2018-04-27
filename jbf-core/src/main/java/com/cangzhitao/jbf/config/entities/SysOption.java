package com.cangzhitao.jbf.config.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_option")
public class SysOption extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3473281374111827591L;
	
	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=200, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@NotEmpty(message="类型不能为空")
	@Size(min=1, max=200, message="名称的长度必须在{min}和{max}之间")
	private String type;
	
	@Size(min=0, max=200, message="描述的长度不能超过{max}")
	private String description;
	
	@NotEmpty(message="值不能为空")
	@Size(min=1, max=200, message="值的长度必须在{min}和{max}之间")
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
