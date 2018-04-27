package com.cangzhitao.jbf.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtil {
	
	private static ApplicationContext ac = null;
	
	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
	public static void setApplicationContext(ApplicationContext ac) {
		SpringUtil.ac = ac;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) getApplicationContext().getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clz) {
		return (T) getApplicationContext().getBean(clz);
	}
	
	public static String getContextPath() {
		if(ac instanceof WebApplicationContext) {
			WebApplicationContext wac = (WebApplicationContext) ac;
			return wac.getServletContext().getContextPath();
		}
		return null;
	}


}
