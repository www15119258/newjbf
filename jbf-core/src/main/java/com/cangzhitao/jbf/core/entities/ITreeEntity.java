package com.cangzhitao.jbf.core.entities;

import java.util.List;

public interface ITreeEntity<T extends BaseEntity> {

	public Long getParent();
	
	public void setParent(Long parent);
	
	public List<T> getChildren();
	
	public void setChildren(List<T> children);
	
	public String getParentName();
	
}
