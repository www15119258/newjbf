package com.cangzhitao.jbf.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.core.annotation.NoCache;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysMenu;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.repository.SysMenuRepository;

@Service
public class SysMenuService extends BaseService<SysMenu, Long> implements ISysMenuService {

	@Autowired
	private SysMenuRepository sysMenuRepository;
	
	@Autowired
	@Lazy
	private ISysRoleMenuRelationService sysRoleMenuRelationService;
	
	@Override
	public BaseRepository<SysMenu, Long> getRepository() {
		return sysMenuRepository;
	}

	@Override
	@NoCache
	public Set<String> getRoleSet(Long menuid) {
		String sql = "SELECT DISTINCT\n" +
				"	t1.id\n" +
				"FROM\n" +
				"	t_sys_role t1,\n" +
				"	t_sys_role_menu t2\n" +
				"WHERE\n" +
				"	t1.id = t2.roleid\n" +
				"AND t1.delete_flag = 0\n" +
				"AND t2.delete_flag = 0\n" +
				"AND t2.menuid = ?";
		List<Map<String, Object>> menuList = getJdbcTemplate().queryForList(sql, new Object[]{menuid});
		Set<String> set = new HashSet<>();
		for(int i=0;i<menuList.size();i++) {
			set.add(menuList.get(i).get("id")+"");
		}
		return set;
	}
	
	@Override
	@NoCache
	@Transactional
	public List<SysRoleMenuRelation> assignRoles(Long menu, String type, Long[] roles) {
		String sql = "select t1.id from t_sys_role_menu t1,t_sys_role t2 where t1.roleid=t2.id and t2.type=? and t1.menuid=?";
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql, type, menu);
		List<Long> roleList = new ArrayList<Long>();
		for(int i=0;i<mapList.size();i++) {
			roleList.add(Long.parseLong(mapList.get(i).get("id")+""));
		}
		if(roleList.size()>0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roles", roleList);
			sql ="delete from t_sys_role_menu where id in (:roles)";
			getNamedParameterJdbcTemplate().update(sql, params);
		}
		List<SysRoleMenuRelation> list = new ArrayList<SysRoleMenuRelation>();
		for(int i=0;i<roles.length;i++) {
			SysRoleMenuRelation sysRoleMenuRelation = new SysRoleMenuRelation();
			sysRoleMenuRelation.setMenuid(menu);
			sysRoleMenuRelation.setRoleid(roles[i]);
			sysRoleMenuRelation.setCreateInfo();
			list.add(sysRoleMenuRelation);
		}
		sysRoleMenuRelationService.save(list);
		return list;
	}

}
