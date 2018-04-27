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
import com.cangzhitao.jbf.security.entities.SysRole;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.entities.SysRoleModuleRelation;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.repository.SysRoleRepository;

@Service
public class SysRoleService extends BaseService<SysRole, Long> implements ISysRoleService {

	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	@Lazy
	private ISysRoleMenuRelationService sysRoleMenuRelationService;
	
	@Autowired
	@Lazy
	private ISysRoleModuleRelationService sysRoleModuleRelationService;
	
	@Override
	public BaseRepository<SysRole, Long> getRepository() {
		return sysRoleRepository;
	}
	
	@Override
	@NoCache
	public Set<String> getMenuSet(Long roleid) {
		String sql = "SELECT DISTINCT\n" +
				"	t1.id\n" +
				"FROM\n" +
				"	t_sys_menu t1,\n" +
				"	t_sys_role_menu t2\n" +
				"WHERE\n" +
				"	t1.id = t2.menuid\n" +
				"AND t1.delete_flag = 0\n" +
				"AND t2.delete_flag = 0\n" +
				"AND t2.roleid = ?";
		List<Map<String, Object>> menuList = getJdbcTemplate().queryForList(sql, new Object[]{roleid});
		Set<String> set = new HashSet<>();
		for(int i=0;i<menuList.size();i++) {
			set.add(menuList.get(i).get("id")+"");
		}
		return set;
	}
	
	@Override
	@NoCache
	public Set<String> getModuleSet(Long roleid) {
		String sql = "SELECT DISTINCT\n" +
				"	t1.id\n" +
				"FROM\n" +
				"	t_sys_module t1,\n" +
				"	t_sys_role_module t2\n" +
				"WHERE\n" +
				"	t1.id = t2.moduleid\n" +
				"AND t1.delete_flag = 0\n" +
				"AND t2.delete_flag = 0\n" +
				"AND t2.roleid = ?";
		List<Map<String, Object>> moduleList = getJdbcTemplate().queryForList(sql, new Object[]{roleid});
		Set<String> set = new HashSet<>();
		for(int i=0;i<moduleList.size();i++) {
			set.add(moduleList.get(i).get("id")+"");
		}
		return set;
	}
	
	@Override
	@NoCache
	@Transactional
	public List<SysRoleMenuRelation> assignMenus(Long role, String type, Long[] menus) {
		String sql = "select t1.id from t_sys_role_menu t1,t_sys_menu t2 where t1.menuid=t2.id and t2.type=? and t1.roleid=?";
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql, type, role);
		List<Long> roleList = new ArrayList<Long>();
		for(int i=0;i<mapList.size();i++) {
			roleList.add(Long.parseLong(mapList.get(i).get("id")+""));
		}
		if(roleList.size()>0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("menus", roleList);
			sql ="delete from t_sys_role_menu where id in (:menus)";
			getNamedParameterJdbcTemplate().update(sql, params);
		}
		List<SysRoleMenuRelation> list = new ArrayList<SysRoleMenuRelation>();
		for(int i=0;i<menus.length;i++) {
			SysRoleMenuRelation sysRoleMenuRelation = new SysRoleMenuRelation();
			sysRoleMenuRelation.setMenuid(menus[i]);
			sysRoleMenuRelation.setRoleid(role);
			sysRoleMenuRelation.setCreateInfo();
			list.add(sysRoleMenuRelation);
		}
		sysRoleMenuRelationService.save(list);
		return list;
	}
	
	@Override
	@NoCache
	@Transactional
	public List<SysRoleModuleRelation> assignModules(Long role, Long[] modules) {
		String sql = "delete from t_sys_role_module where roleid=?";
		getJdbcTemplate().update(sql, role);
		List<SysRoleModuleRelation> list = new ArrayList<SysRoleModuleRelation>();
		for(int i=0;i<modules.length;i++) {
			SysRoleModuleRelation sysRoleModuleRelation = new SysRoleModuleRelation();
			sysRoleModuleRelation.setModuleid(modules[i]);
			sysRoleModuleRelation.setRoleid(role);
			sysRoleModuleRelation.setCreateInfo();
			list.add(sysRoleModuleRelation);
		}
		sysRoleModuleRelationService.save(list);
		return list;
	}

	@Override
	@NoCache
	public Page<SysUser> getUsersById(Long roleid, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SysUser> criteriaQuery = criteriaBuilder.createQuery(SysUser.class);
		EntityType<SysUserRoleRelation> userRoleEntity = getEntityManager().getMetamodel().entity(SysUserRoleRelation.class);
		Root<SysUser> userRoot = criteriaQuery.from(SysUser.class);
		Subquery<SysUserRoleRelation> subQuery = criteriaQuery.subquery(SysUserRoleRelation.class);
		Root<SysUserRoleRelation> sysUserRoleroot = subQuery.from(SysUserRoleRelation.class);
		subQuery.where(criteriaBuilder.equal(sysUserRoleroot.get(userRoleEntity.getSingularAttribute("roleid")), roleid));
		criteriaQuery.where(criteriaBuilder.equal(userRoot.get("deleteFlag"), false), userRoot.get("id").in(subQuery.select(sysUserRoleroot.get("userid"))));
		if(pageable.getSort()!=null) {
			Iterator<Order> it = pageable.getSort().iterator();
			while(it.hasNext()) {
				Order order = it.next();
				if(order.isDescending()) {
					criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(order.getProperty())));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(order.getProperty())));
				}
			}
		}
		TypedQuery<SysUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<SysUser> list = typedQuery.getResultList();
		String sql = "select count(1) from t_sys_user_role t1,t_sys_user t2 where t1.userid=t2.id and t2.delete_flag=0 and t1.roleid=?";
		long count = getJdbcTemplate().queryForObject(sql, Long.class, roleid);
		PageImpl<SysUser> page = new PageImpl<SysUser>(list, pageable, count);
		return page;
	}

	@Override
	@NoCache
	public Page<SysUser> getUnSelectUsersById(Long roleid, Map<String, Object> paramMap, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SysUser> criteriaQuery = criteriaBuilder.createQuery(SysUser.class);
		EntityType<SysUserRoleRelation> userRoleEntity = getEntityManager().getMetamodel().entity(SysUserRoleRelation.class);
		Root<SysUser> userRoot = criteriaQuery.from(SysUser.class);
		Subquery<SysUserRoleRelation> subQuery = criteriaQuery.subquery(SysUserRoleRelation.class);
		Root<SysUserRoleRelation> sysUserRoleroot = subQuery.from(SysUserRoleRelation.class);
		subQuery.where(criteriaBuilder.equal(sysUserRoleroot.get(userRoleEntity.getSingularAttribute("roleid")), roleid));
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(criteriaBuilder.equal(userRoot.get("deleteFlag"), false));
		if(paramMap!=null&&paramMap.size()>0) {
			Iterator<String> it = paramMap.keySet().iterator();
			while(it.hasNext()) {
				String prop = it.next();
				Object value = paramMap.get(prop);
				predicates.add(criteriaBuilder.equal(userRoot.get(prop), value));
			}
		}
		predicates.add(userRoot.get("id").in(subQuery.select(sysUserRoleroot.get("userid"))).not());
		criteriaQuery.where(predicates.toArray(new Predicate[]{}));
		if(pageable.getSort()!=null) {
			Iterator<Order> it = pageable.getSort().iterator();
			while(it.hasNext()) {
				Order order = it.next();
				if(order.isDescending()) {
					criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(order.getProperty())));
				} else {
					criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(order.getProperty())));
				}
			}
		}
		TypedQuery<SysUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		typedQuery.setMaxResults(pageable.getPageSize());
		List<SysUser> list = typedQuery.getResultList();
		String sql = "select count(1) from t_sys_user t3 where t3.id not in (select t2.id from t_sys_user_role t1,t_sys_user t2 where t1.userid=t2.id and t2.delete_flag=0 and t1.roleid=?)";
		long count = getJdbcTemplate().queryForObject(sql, Long.class, roleid);
		PageImpl<SysUser> page = new PageImpl<SysUser>(list, pageable, count);
		return page;
	}
	
}
