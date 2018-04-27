package com.cangzhitao.jbf.security.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.entities.SysUserRoleRelation;
import com.cangzhitao.jbf.security.service.ISysUserService;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/security/user")  
public class SysUserController extends BaseController {
	
	@Autowired
	private ISysUserService sysUserService;

	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysUser sysUser) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysUser> page = sysUserService.findAll(sysUser, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryRoles/{username}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryRoles(@PathVariable String username) {
		if(username==null||"".equals(username)) {
			return new HashSet<String>();
		}
		Set<String> roles = UserManager.getRoleSetByUsername(username);
		return roles;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysUser sysUser = sysUserService.findById(id);
		if(sysUser!=null) {
			resultBean.setData(sysUser);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.user.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysUser sysUser) {
		ResultBean resultBean = new ResultBean();
		sysUser.setCreateInfo();
		validate(sysUser.validate());
		SysUser example = new SysUser().setNull().setProperty("username", sysUser.getUsername()).setProperty("deleteFlag", false);
		List<SysUser> users = sysUserService.findAll(example);
		if(users!=null&&users.size()>0) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("用户名已经存在！");
			return resultBean;
		}
		example = new SysUser().setNull().setProperty("email", sysUser.getEmail()).setProperty("deleteFlag", false);
		users = sysUserService.findAll(example);
		if(users!=null&&users.size()>0) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("Email已经存在！");
			return resultBean;
		}
		sysUser = sysUserService.save(sysUser);
		resultBean.setData(sysUser);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.user.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysUser sysUser) {
		ResultBean resultBean = new ResultBean();
		if(sysUser.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysUser old = sysUserService.findById(sysUser.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setNickname(sysUser.getNickname());
		old.setIcon(sysUser.getIcon());
		old.setEmail(sysUser.getEmail());
		old.setMobile(sysUser.getMobile());
		old.setSex(sysUser.getSex());
		old.setAge(sysUser.getAge());
		old.setBirthday(sysUser.getBirthday());
		old.setStatus(sysUser.getStatus());
		old.setUpdateInfo();
		validate(old.validate());
		SysUser example = new SysUser().setNull().setProperty("email", old.getEmail()).setProperty("deleteFlag", false);
		List<SysUser> users = sysUserService.findAll(example);
		if(users!=null&&users.size()>0) {
			for(int i=0;i<users.size();i++) {
				if(users.get(i).getId() != old.getId()) {
					resultBean.setStatus(ResultBean.ERROR);
					resultBean.setMessage("Email已经存在！");
					return resultBean;
				}
			}
		}
		sysUser = sysUserService.save(old);
		resultBean.setData(sysUser);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.user.assignRoles")
	@RequestMapping(value={"assignRoles"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean assignRoles(String user, String type, String roles) {
		ResultBean resultBean = new ResultBean();
		if(user==null||"".equals(user)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("用户不存在！");
			return resultBean;
		}
		List<Long> roleList = new ArrayList<Long>();
		if(roles!=null&&!"".equals(roles)) {
			String[] rs = roles.split(",");
			for(int i=0;i<rs.length;i++) {
				roleList.add(Long.parseLong(rs[i]));
			}
		}
		List<SysUserRoleRelation> sysUserRoleRelations = sysUserService.assignRoles(Long.parseLong(user), type, roleList.toArray(new Long[]{}));
		resultBean.setData(sysUserRoleRelations);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.user.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysUser sysUser = sysUserService.findById(id);
		if(sysUser!=null) {
			sysUser.setLogicDelete();
			sysUserService.save(sysUser);
			resultBean.setData(sysUser);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
