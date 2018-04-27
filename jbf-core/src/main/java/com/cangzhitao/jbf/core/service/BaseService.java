package com.cangzhitao.jbf.core.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.core.entities.ITreeEntity;
import com.cangzhitao.jbf.core.examplematcher.annotation.StringContain;
import com.cangzhitao.jbf.core.examplematcher.annotation.StringEnd;
import com.cangzhitao.jbf.core.examplematcher.annotation.StringExact;
import com.cangzhitao.jbf.core.examplematcher.annotation.StringIgnoreCase;
import com.cangzhitao.jbf.core.examplematcher.annotation.StringStart;
import com.cangzhitao.jbf.core.service.base.IBaseService;
import com.cangzhitao.jbf.core.util.ReflectUtil;

public abstract class BaseService<T extends BaseEntity, ID extends Serializable> implements IBaseService<T, ID> {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private EntityManager entityManager;
	
	private Class<T> entityClass = null;
	
	public BaseService() {
		entityClass = getObjectClass();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getObjectClass() {
	    Type type = getClass().getGenericSuperclass();
//	    Type type = getClass().getSuperclass().getGenericSuperclass();
	    if (type instanceof ParameterizedType) {
	    	ParameterizedType pt = (ParameterizedType)type;
	    	Type[] types = pt.getActualTypeArguments();
	    	return (Class<T>)types[0];
	    }
	    return (Class<T>) type;
	}
	
	@Override
	@Transactional
	public T save(T entity) {
		return this.getRepository().save(entity);
	}
	
	@Override
	@Transactional
	public List<T> save(Iterable<T> entities) {
		return this.getRepository().saveAll(entities);
	}
	
	@Override
	@Transactional
	public void delete(T entity) {
		this.getRepository().delete(entity);
	}
	
	@Override
	@Transactional
	public void deleteById(ID id) {
		this.getRepository().deleteById(id);
	}
	
	@Override
	@Transactional
	public void deleteInBatch(Iterable<T> entities) {
		this.getRepository().deleteInBatch(entities);
	}
	
	public List<T> findAll() {
		return this.getRepository().findAll();
	}
	
	public List<T> findAll(T entity) {
		return this.getRepository().findAll(getExample(entity));
	}
	
	public List<T> findAll(T entity, Sort sort) {
		return getRepository().findAll(getExample(entity), sort);
	}
	
	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}
	
	public List<T> findAll(Iterable<ID> ids) {
		return getRepository().findAllById(ids);
	}
	
	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}
	
	public Page<T> findAll(T entity, Pageable pageable) {
		return getRepository().findAll(getExample(entity), pageable);
	}
	
	public T findOne(T entity) {
		Optional<T> t = getRepository().findOne(getExample(entity));
		if(t.isPresent()) {
			return t.get();
		}
		return null;
	}
	
	public T findById(ID id) {
		if(id==null) {
			return null;
		}
		Optional<T> t = getRepository().findById(id);
		if(t.isPresent()) {
			return t.get();
		}
		return null;
	}

	public Example<T> getExample(T entity) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching();
		Map<String, Field> fieldMap = ReflectUtil.getAllFields(entity.getClass());
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
			if(field.getType()==String.class) {
				//把空字符的置为空，则查询不会加入该条件
				if("".equals(ReflectUtil.getFiled(entity, name))) {
					ReflectUtil.setField(entity, name, null);
				}
				if(field.getAnnotation(StringContain.class)!=null) {
					exampleMatcher = exampleMatcher.withMatcher(name, GenericPropertyMatcher.of(StringMatcher.CONTAINING, field.getAnnotation(StringIgnoreCase.class)!=null));
				} else if(field.getAnnotation(StringStart.class)!=null) {
					exampleMatcher = exampleMatcher.withMatcher(name, GenericPropertyMatcher.of(StringMatcher.STARTING, field.getAnnotation(StringIgnoreCase.class)!=null));
				} else if(field.getAnnotation(StringEnd.class)!=null) {
					exampleMatcher = exampleMatcher.withMatcher(name, GenericPropertyMatcher.of(StringMatcher.ENDING, field.getAnnotation(StringIgnoreCase.class)!=null));
				} else if(field.getAnnotation(StringExact.class)!=null) {
					exampleMatcher = exampleMatcher.withMatcher(name, GenericPropertyMatcher.of(StringMatcher.EXACT, field.getAnnotation(StringIgnoreCase.class)!=null));
				} else {
					exampleMatcher = exampleMatcher.withMatcher(name, GenericPropertyMatcher.of(StringMatcher.EXACT, field.getAnnotation(StringIgnoreCase.class)!=null));
				}
			}
		}
		Example<T> example = Example.of(entity, exampleMatcher);
		return example;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findFullTree(T allEntities) {
		if (!(allEntities instanceof ITreeEntity)) {
			return new ArrayList<T>();
		}
		if (!(allEntities instanceof BaseEntity)) {
			return new ArrayList<T>();
		}
		Sort sort = new Sort(Direction.ASC, "sort");
		List<T> list = findAll(allEntities, sort);
		Map<Long, T> map = new HashMap<Long, T>();
		for(int i=0;i<list.size();i++) {
			T t = list.get(i);
			map.put(t.getId(), t);
		}
		List<T> rootList = new ArrayList<T>();
		for(int i=0;i<list.size();i++) {
			ITreeEntity<T> t = (ITreeEntity<T>) list.get(i);
			if(t.getParent()==null) {
				rootList.add((T) t);
			} else {
				ITreeEntity<T> parent = map.get(t.getParent())==null?null:(ITreeEntity<T>)map.get(t.getParent());
				if(parent==null) {
					continue;
				} else {
					List<T> children = parent.getChildren();
					if(children==null) {
						children = new ArrayList<T>();
					}
					children.add((T) t);
					parent.setChildren(children);
				}
			}
		}
		return (List<T>) rootList;
	}
	
	public List<T> findTreeChildren(Long parent) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		EntityType<T> entity = entityManager.getMetamodel().entity(entityClass);
		Root<T> root = criteriaQuery.from(entityClass);
		if(parent==null) {
			criteriaQuery.where(criteriaBuilder.isNull(root.get(entity.getSingularAttribute("parent", Long.class))), criteriaBuilder.equal(root.get(entity.getSingularAttribute("deleteFlag", Boolean.class)), false));
		} else {
			criteriaQuery.where(criteriaBuilder.equal(root.get(entity.getSingularAttribute("parent", Long.class)), parent), criteriaBuilder.equal(root.get(entity.getSingularAttribute("deleteFlag", Boolean.class)), false));
		}
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> result = typedQuery.getResultList();
		return result;
	}
	
	public List<T> findTreeAllChildren(Long parent) {
		List<T> children = new ArrayList<T>();
		this.findTreeAllChildren(children, parent);
		return children;
	}
	
	private void findTreeAllChildren(List<T> children, Long parent) {
		if(children==null) {
			children = new ArrayList<T>();
		}
		List<T> list = this.findTreeChildren(parent);
		if(list!=null&&list.size()>0) {
			children.addAll(list);
			for(int i=0;i<list.size();i++) {
				this.findTreeAllChildren(children, list.get(i).getId());
			}
		}
		
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
}
