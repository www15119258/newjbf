package com.cangzhitao.jbf.security.shiro;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.jbf.core.annotation.ValidateException;
import com.cangzhitao.jbf.core.util.JsonUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Component
public class ShiroExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if(ex instanceof IllegalStateException) {
			return null;
		}
		if(ex instanceof ValidateException) {
			ResultBean resultBean = new ResultBean();
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage(ex.getMessage());
			try {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(JsonUtil.toJSONString(resultBean));
				response.getWriter().flush();
				response.getWriter().close();
				return null;
			} catch(Exception e) {
			}
		}
		if(ex instanceof UnauthenticatedException) {
			response.setStatus(401);
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().print("未登录！");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
			}
			return null;
		}
		if(ex instanceof UnauthorizedException || ex instanceof UnauthenticatedException){  
			Subject subject = SecurityUtils.getSubject();
			if(subject.isAuthenticated()) {
				response.setStatus(403);
				response.setContentType("application/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().print("无权限！");
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
				}
				return null;
			} else {
				response.setStatus(401);
				response.setContentType("application/json;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().print("未登录！");
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
				}
				return null;
			}
        }
		return null;
	}
	

}
