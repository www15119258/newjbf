package com.cangzhitao.jbf.security.init;

import com.cangzhitao.jbf.core.util.SpringUtil;
import com.cangzhitao.jbf.security.entities.SysMenu;
import com.cangzhitao.jbf.security.service.ISysMenuService;

public class SecurityDataInit {

	public static void initMenu() {
		ISysMenuService servcie = SpringUtil.getBean(ISysMenuService.class);
		SysMenu sysMenu = null;
		SysMenu parent = null;
		sysMenu = new SysMenu();
		sysMenu.setName("仪表盘");
		sysMenu.setUrl("admin/dashboard");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("系统设置");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		servcie.save(sysMenu);
		
		parent = sysMenu;
		sysMenu = new SysMenu();
		sysMenu.setName("字典设置");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/dict");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("用户权限管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		servcie.save(sysMenu);
		
		parent = sysMenu;
		sysMenu = new SysMenu();
		sysMenu.setName("用户管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/user");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("角色管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/role");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("目录管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/menu");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("模块管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/module");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
		
		sysMenu = new SysMenu();
		sysMenu.setName("权限管理");
		sysMenu.setType("left");
		sysMenu.setIcon("dashboard");
		sysMenu.setUrl("admin/perm");
		sysMenu.setParent(parent.getId());
		servcie.save(sysMenu);
	}
	
}
