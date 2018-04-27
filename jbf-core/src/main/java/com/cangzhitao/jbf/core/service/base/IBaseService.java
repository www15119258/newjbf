package com.cangzhitao.jbf.core.service.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;

public interface IBaseService<T extends BaseEntity, ID extends Serializable> {
	
	public BaseRepository<T, ID> getRepository();
	
	public T save(T entity);
	
	public List<T> save(Iterable<T> entities);
	
	public void delete(T entity);
	
	public void deleteById(ID id);
	
	public void deleteInBatch(Iterable<T> entities);
	
	public List<T> findAll();
	
	public List<T> findAll(T entity);
	
	public List<T> findAll(T entity, Sort sort);
	
	public List<T> findAll(Sort sort);
	
	public List<T> findAll(Iterable<ID> ids);
	
	public Page<T> findAll(Pageable pageable);
	
	public Page<T> findAll(T entity, Pageable pageable);
	
	public List<T> findFullTree(T allEntities);
	
	public List<T> findTreeChildren(Long parent);
	
	public List<T> findTreeAllChildren(Long parent);
	
	public T findOne(T entity);
	
	public T findById(ID id);
	
}
