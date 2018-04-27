package com.cangzhitao.jbf.core.entities;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.cangzhitao.jbf.core.util.ReflectUtil;
import com.cangzhitao.jbf.validation.Validator;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.groups.DEFAULT;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154046450711849835L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "create_by")
	private Long createBy;
	
	@Column(name = "create_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	
	@Column(name = "update_by")
	private Long updateBy;
	
	@Column(name = "update_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	@Column(name = "delete_flag")
	@NotNull
	private Boolean deleteFlag = false;
	
	@Column(name = "sort")
	private Integer sort = 100;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T setProperty(String name, Object value) {
		ReflectUtil.setField(this, name, value);
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BaseEntity> T setNull() {
		Map<String, Field> fieldMap = ReflectUtil.getAllFields(this.getClass());
		Iterator<String> it = fieldMap.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			Field field = fieldMap.get(name);
			if(field.getAnnotation(Transient.class)!=null) {
				continue;
			}
			if(Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			ReflectUtil.setField(this, name, null);
		}
		return (T) this;
	}
	
	public void setCreateInfo() {
		Date date = new Date();
		this.setCreateDate(date);
		this.setUpdateDate(date);
		Object userid = null;
		try {
			userid = Class.forName("com.cangzhitao.jbf.security.util").getMethod("getCurrentUser").invoke(null);
		} catch (Exception e) {
		}
		if(userid!=null) {
			this.setCreateBy((Long) userid);
			this.setUpdateBy((Long) userid);
		}
	}
	
	public void setUpdateInfo() {
		Date date = new Date();
		this.setUpdateDate(date);
		Object userid = null;
		try {
			userid = Class.forName("com.cangzhitao.jbf.security.util").getMethod("getCurrentUser").invoke(null);
		} catch (Exception e) {
		}
		if(userid!=null) {
			this.setUpdateBy((Long) userid);
		}
	}
	
	public void setLogicDelete() {
		Date date = new Date();
		this.setUpdateDate(date);
		Object userid = null;
		try {
			userid = Class.forName("com.cangzhitao.jbf.security.util").getMethod("getCurrentUser").invoke(null);
		} catch (Exception e) {
		}
		if(userid!=null) {
			this.setUpdateBy((Long) userid);
		}
		this.setDeleteFlag(true);
	}
	
	public List<Map<String, String>> validate(Class<?>... groups) {
		if(groups==null||groups.length==0) {
			groups = new Class<?>[]{DEFAULT.class};
		}
		return Validator.validate(this, groups);
	}
	
}
