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

import com.cangzhitao.jbf.core.annotation.CacheService;
import com.cangzhitao.jbf.core.annotation.NoCache;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.repository.SysUserRepository;

@Service
@CacheService
public class SysUserService extends BaseService<SysUser, Long> implements ISysUserService {

	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Autowired
	@Lazy
	private ISysUserRoleRelationService sysUserRoleRelationService;
	
	@Override
	public BaseRepository<SysUser, Long> getRepository() {
		return sysUserRepository;
	}

	@Override
	@NoCache
	public Set<String> getPermSet(String username) {
		String sql = "SELECT DISTINCT\n" +
				"	t4.perm\n" +
				"FROM\n" +
				"	t_sys_user_role t1,\n" +
				"	t_sys_role_module t2,\n" +
				"	t_sys_module_perm t3,\n" +
				"	t_sys_perm t4,\n" +
				"	t_sys_user t5,\n" +
				"	t_sys_role t6,\n" +
				"	t_sys_module t7\n" +
				"WHERE\n" +
				"	t1.roleid = t2.roleid\n" +
				"AND t2.moduleid = t3.moduleid\n" +
				"AND t3.permid = t4.id\n" +
				"AND t1.userid = t5.id\n" +
				"AND t1.roleid = t6.id\n" +
				"AND t3.moduleid = t7.id\n" +
				"AND t1.delete_flag = 0\n" +
				"AND t2.delete_flag = 0\n" +
				"AND t3.delete_flag = 0\n" +
				"AND t4.delete_flag = 0\n" +
				"AND t5.delete_flag = 0\n" +
				"AND t6.delete_flag = 0\n" +
				"AND t7.delete_flag = 0\n" +
				"AND t7.status = 1 \n" +
				"AND t6.status = 1 \n" +
				"AND t4.status = 1 \n" +
				"AND t5.username = ?";
		List<Map<String, Object>> permList = getJdbcTemplate().queryForList(sql, new Object[]{username});
		Set<String> set = new HashSet<>();
		for(int i=0;i<permList.size();i++) {
			set.add(permList.get(i).get("perm")+"");
		}
		return set;
	}
	
	@Override
	@NoCache
	public Set<String> getRoleSet(String username) {
		String sql = "SELECT DISTINCT\n" +
				"	t3.id\n" +
				"FROM\n" +
				"	t_sys_user_role t1,\n" +
				"	t_sys_user t2,\n" +
				"	t_sys_role t3\n" +
				"WHERE\n" +
				"	t1.userid = t2.id\n" +
				"AND t1.roleid = t3.id\n" +
				"AND t3.delete_flag = 0\n" +
				"AND t2.username = ?";
		List<Map<String, Object>> permList = getJdbcTemplate().queryForList(sql, new Object[]{username});
		Set<String> set = new HashSet<>();
		for(int i=0;i<permList.size();i++) {
			set.add(permList.get(i).get("id")+"");
		}
		return set;
	}

	@Override
	@NoCache
	@Transactional
	public List<SysUserRoleRelation> assignRoles(Long user, String type, Long[] roles) {
		String sql = "select t1.id from t_sys_user_role t1,t_sys_role t2 where t1.roleid=t2.id and t2.type=? and t1.userid=?";
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql, type, user);
		List<Long> roleList = new ArrayList<Long>();
		for(int i=0;i<mapList.size();i++) {
			roleList.add(Long.parseLong(mapList.get(i).get("id")+""));
		}
		if(roleList.size()>0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roles", roleList);
			sql ="delete from t_sys_user_role where id in (:roles)";
			getNamedParameterJdbcTemplate().update(sql, params);
		}
		List<SysUserRoleRelation> list = new ArrayList<SysUserRoleRelation>();
		for(int i=0;i<roles.length;i++) {
			SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
			sysUserRoleRelation.setUserid(user);
			sysUserRoleRelation.setRoleid(roles[i]);
			sysUserRoleRelation.setCreateInfo();
			list.add(sysUserRoleRelation);
		}
		sysUserRoleRelationService.save(list);
		return list;
	}
}
