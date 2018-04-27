package com.cangzhitao.jbf.config.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.core.enums.Visible;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_dict")
public class SysDict extends BaseEntity implements ITreeEntity<SysDict> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5063111202999766865L;

	@NotEmpty(message="类型不能为空")
	@Size(min=3, max=50, message="类型长度必须在{min}和{max}之间")
	private String type;
	
	@NotEmpty(message="值不能为空")
	private String value;
	
	@NotEmpty(message="标签不能为空")
	private String label;
	
	private String description;
	
	@NotNull(message="是否可见不能为空")
	private Visible visible = Visible.TRUE;
	
	private Long parent;
	
	@Transient
	private String parentName;
	
	@Transient
	private List<SysDict> children;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Visible getVisible() {
		return visible;
	}

	public void setVisible(Visible visible) {
		this.visible = visible;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public List<SysDict> getChildren() {
		return children;
	}

	public void setChildren(List<SysDict> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
