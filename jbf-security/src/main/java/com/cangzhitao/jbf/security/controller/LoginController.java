package com.cangzhitao.jbf.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}") 
public class LoginController {
	
	@RequestMapping(value={"login.do"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean login(UsernamePasswordToken token) {
		ResultBean resultBean = new ResultBean();
		Subject subject = SecurityUtils.getSubject();
        try {
        	subject.login(token);
        } catch(UnknownAccountException e) {
        	resultBean.setStatus(ResultBean.ERROR);
        	resultBean.setMessage("用户不存在！");
        	return resultBean;
        } catch(AuthenticationException e) {
        	resultBean.setStatus(ResultBean.ERROR);
        	resultBean.setMessage("密码错误！");
        	return resultBean;
        }
		resultBean.setStatus(ResultBean.SUCCESS);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", UserManager.getCurrentUser());
		map.put("perms", UserManager.getPermSet());
		resultBean.setData(map);
		return resultBean;
	}
	
	@RequestMapping(value={"logout.do"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean logout() {
		ResultBean resultBean = new ResultBean();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		resultBean.setStatus(ResultBean.SUCCESS);
		return resultBean;
	}
	
}
