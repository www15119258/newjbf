package com.cangzhitao.jbf.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cangzhitao.jbf.core.util.RequestResponseContextUtil;

/**
 * 用来获取每个线程的request和reponse工具
 * @author lihui
 *
 */
@Component
public class RequestResponseInterceptor extends HandlerInterceptorAdapter {


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		RequestResponseContextUtil.setRequest(request);
		RequestResponseContextUtil.setResponse(response);
		return super.preHandle(request, response, handler);
	}

}
