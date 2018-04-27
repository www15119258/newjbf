package com.cangzhitao.jbf.cms.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_cms_tagcloud")
public class Tagcloud extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1328449146015876562L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=200, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")			
	private Date lastUse;
	
	private Integer refers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastUse() {
		return lastUse;
	}

	public void setLastUse(Date lastUse) {
		this.lastUse = lastUse;
	}

	public Integer getRefers() {
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}
	
}
