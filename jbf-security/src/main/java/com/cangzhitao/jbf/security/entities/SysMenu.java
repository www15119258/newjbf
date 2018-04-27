package com.cangzhitao.jbf.security.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.core.enums.Visible;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_menu")
public class SysMenu extends BaseEntity implements ITreeEntity<SysMenu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7721742959215846208L;

	@NotEmpty(message="名称不能为空")
	@Size(min=1, max=20, message="名称的长度必须在{min}和{max}之间")
	private String name;
	
	@Size(min=0, max=200, message="URL的长度不能超过{max}之间")
	private String url;
	
	@NotEmpty(message="类型不能为空")
	private String type;
	
	private String icon;
	
	private Long parent;
	
	@Lob
	private String i18n;
	
	@NotNull(message="是否可见不能为空")
	private Visible visible = Visible.TRUE;
	
	@Transient
	private String parentName;

	@Transient
	private List<SysMenu> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}

	public Visible getVisible() {
		return visible;
	}

	public void setVisible(Visible visible) {
		this.visible = visible;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
