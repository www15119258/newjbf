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
import com.cangzhitao.jbf.security.entities.SysMenu;
import com.cangzhitao.jbf.security.entities.SysRoleMenuRelation;
import com.cangzhitao.jbf.security.service.ISysMenuService;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/security/menu")
public class SysMenuController extends BaseController {

	@Autowired
	private ISysMenuService sysMenuService;
	
	@RequiresAuthentication
	@RequestMapping(value={"queryTree/{type}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryTree(@PathVariable String type) {
		SysMenu sysMenu = new SysMenu().setNull().setProperty("type", type).setProperty("deleteFlag", false);
		List<SysMenu> sysMenuTree = sysMenuService.findFullTree(sysMenu);
		return sysMenuTree;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysMenu sysMenu) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysMenu> page = sysMenuService.findAll(sysMenu, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysMenu sysMenu = sysMenuService.findById(id);
		if(sysMenu!=null) {
			resultBean.setData(sysMenu);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryRoles/{menu}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryRoles(@PathVariable Long menu) {
		if(menu==null) {
			return new HashSet<String>();
		}
		Set<String> roles = UserManager.getRoleSetByMenuid(menu);
		return roles;
	}
	
	@RequiresPermissions(value="jbf.security.menu.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysMenu sysMenu) {
		ResultBean resultBean = new ResultBean();
		sysMenu.setCreateInfo();
		validate(sysMenu.validate());
		sysMenu = sysMenuService.save(sysMenu);
		resultBean.setData(sysMenu);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.menu.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysMenu sysMenu) {
		ResultBean resultBean = new ResultBean();
		if(sysMenu.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysMenu old = sysMenuService.findById(sysMenu.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(sysMenu.getName());
		old.setUrl(sysMenu.getUrl());
		old.setType(sysMenu.getType());
		old.setIcon(sysMenu.getIcon());
		old.setParent(sysMenu.getParent());
		old.setI18n(sysMenu.getI18n());
		old.setVisible(sysMenu.getVisible());
		old.setUpdateInfo();
		validate(old.validate());
		sysMenu = sysMenuService.save(old);
		resultBean.setData(sysMenu);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.menu.assignRoles")
	@RequestMapping(value={"assignRoles"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean assignRoles(Long menu, String type, String roles) {
		ResultBean resultBean = new ResultBean();
		if(menu==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("目录不存在！");
			return resultBean;
		}
		List<Long> roleList = new ArrayList<Long>();
		if(roles!=null&&!"".equals(roles)) {
			String[] rs = roles.split(",");
			for(int i=0;i<rs.length;i++) {
				roleList.add(Long.parseLong(rs[i]));
			}
		}
		List<SysRoleMenuRelation> sysRoleMenuRelations = sysMenuService.assignRoles(menu, type, roleList.toArray(new Long[]{}));
		resultBean.setData(sysRoleMenuRelations);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.menu.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysMenu sysMenu = sysMenuService.findById(id);
		if(sysMenu!=null) {
			List<SysMenu> children = sysMenuService.findTreeAllChildren(sysMenu.getId());
			children.add(sysMenu);
			for(int i=0;i<children.size();i++) {
				children.get(i).setLogicDelete();
			}
			sysMenuService.save(children);
			resultBean.setData(children);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
}
