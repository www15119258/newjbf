package com.cangzhitao.jbf.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.annotation.NoCache;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysModule;
import com.cangzhitao.jbf.security.entities.SysModulePermRelation;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.repository.SysModuleRepository;

@Service
public class SysModuleService extends BaseService<SysModule, Long> implements ISysModuleService {

	@Autowired
	private SysModuleRepository sysModuleRepository;
	
	@Autowired
	@Lazy
	private ISysRoleModuleRelationService sysRoleModuleRelationService;
	
	@Override
	public BaseRepository<SysModule, Long> getRepository() {
		return sysModuleRepository;
	}

	@Override
	@NoCache
	public Set<String> getRoleSet(Long module) {
		String sql = "SELECT DISTINCT\n" +
				"	t1.id\n" +
				"FROM\n" +
				"	t_sys_role t1,\n" +
				"	t_sys_role_module t2\n" +
				"WHERE\n" +
				"	t1.id = t2.roleid\n" +
				"AND t1.delete_flag = 0\n" +
				"AND t2.delete_flag = 0\n" +
				"AND t2.moduleid = ?";
		List<Map<String, Object>> menuList = getJdbcTemplate().queryForList(sql, new Object[]{module});
		Set<String> set = new HashSet<>();
		for(int i=0;i<menuList.size();i++) {
			set.add(menuList.get(i).get("id")+"");
		}
		return set;
	}
	
	@Override
	@NoCache
	@Transactional
	public List<SysRoleModuleRelation> assignRoles(Long module, String type, Long[] roles) {
		String sql = "select t1.id from t_sys_role_module t1,t_sys_role t2 where t1.roleid=t2.id and t2.type=? and t1.moduleid=?";
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql, type, module);
		List<Long> roleList = new ArrayList<Long>();
		for(int i=0;i<mapList.size();i++) {
			roleList.add(Long.parseLong(mapList.get(i).get("id")+""));
		}
		if(roleList.size()>0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roles", roleList);
			sql ="delete from t_sys_role_module where id in (:roles)";
			getNamedParameterJdbcTemplate().update(sql, params);
		}
		List<SysRoleModuleRelation> list = new ArrayList<SysRoleModuleRelation>();
		for(int i=0;i<roles.length;i++) {
			SysRoleModuleRelation sysRoleModuleRelation = new SysRoleModuleRelation();
			sysRoleModuleRelation.setModuleid(module);;
			sysRoleModuleRelation.setRoleid(roles[i]);
			sysRoleModuleRelation.setCreateInfo();
			list.add(sysRoleModuleRelation);
		}
		sysRoleModuleRelationService.save(list);
		return list;
	}

	@Override
	@NoCache
	public Page<SysPerm> getPermsById(Long moduleid, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SysPerm> criteriaQuery = criteriaBuilder.createQuery(SysPerm.class);
		EntityType<SysModulePermRelation> modulePermEntity = getEntityManager().getMetamodel().entity(SysModulePermRelation.class);
		Root<SysPerm> permRoot = criteriaQuery.from(SysPerm.class);
		Subquery<SysModulePermRelation> subQuery = criteriaQuery.subquery(SysModulePermRelation.class);
		Root<SysModulePermRelation> sysModulePermroot = subQuery.from(SysModulePermRelation.class);
		subQuery.where(criteriaBuilder.equal(sysModulePermroot.get(modulePermEntity.getSingularAttribute("moduleid")), moduleid));
		criteriaQuery.where(criteriaBuilder.equal(permRoot.get("deleteFlag"), false), permRoot.get("id").in(subQuery.select(sysModulePermroot.get("permid"))));
		if(pageable.getSort()!=null) {
			Iterator<Order> it = pageable.getSort().iterator();
			while(it.hasNext()) {
				Order order = it.next();
				if(order.isDescending()) {
					criteriaQuery.orderBy(criteriaBuilder.desc(permRoot.get(order.getProperty())));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.asc(permRoot.get(order.getProperty())));
				}
			}
		}
		TypedQuery<SysPerm> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<SysPerm> list = typedQuery.getResultList();
		String sql = "select count(1) from t_sys_module_perm t1,t_sys_perm t2 where t1.permid=t2.id and t2.delete_flag=0 and t1.moduleid=?";
		long count = getJdbcTemplate().queryForObject(sql, Long.class, moduleid);
		PageImpl<SysPerm> page = new PageImpl<SysPerm>(list, pageable, count);
		return page;
	}
	
	@Override
	@NoCache
	public Page<SysPerm> getUnSelectPermsById(Long moduleid, Map<String, Object> paramMap, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SysPerm> criteriaQuery = criteriaBuilder.createQuery(SysPerm.class);
		EntityType<SysModulePermRelation> modulePermEntity = getEntityManager().getMetamodel().entity(SysModulePermRelation.class);
		Root<SysPerm> permRoot = criteriaQuery.from(SysPerm.class);
		Subquery<SysModulePermRelation> subQuery = criteriaQuery.subquery(SysModulePermRelation.class);
		Root<SysModulePermRelation> sysModulePermroot = subQuery.from(SysModulePermRelation.class);
		subQuery.where(criteriaBuilder.equal(sysModulePermroot.get(modulePermEntity.getSingularAttribute("moduleid")), moduleid));
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(permRoot.get("deleteFlag"), false));
		if(paramMap!=null&&paramMap.size()>0) {
			Iterator<String> it = paramMap.keySet().iterator();
			while(it.hasNext()) {
				String prop = it.next();
				Object value = paramMap.get(prop);
				predicates.add(criteriaBuilder.equal(permRoot.get(prop), value));
			}
		}
		predicates.add(permRoot.get("id").in(subQuery.select(sysModulePermroot.get("permid"))).not());
		criteriaQuery.where(predicates.toArray(new Predicate[]{}));
		if(pageable.getSort()!=null) {
			Iterator<Order> it = pageable.getSort().iterator();
			while(it.hasNext()) {
				Order order = it.next();
				if(order.isDescending()) {
					criteriaQuery.orderBy(criteriaBuilder.desc(permRoot.get(order.getProperty())));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.asc(permRoot.get(order.getProperty())));
				}
			}
		}
		TypedQuery<SysPerm> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<SysPerm> list = typedQuery.getResultList();
		String sql = "select count(1) from t_sys_perm t3 where t3.id not in (select t2.id from t_sys_module_perm t1,t_sys_perm t2 where t1.permid=t2.id and t2.delete_flag=0 and t1.moduleid=?)";
		long count = getJdbcTemplate().queryForObject(sql, Long.class, moduleid);
		PageImpl<SysPerm> page = new PageImpl<SysPerm>(list, pageable, count);
		return page;
	}
	
}
