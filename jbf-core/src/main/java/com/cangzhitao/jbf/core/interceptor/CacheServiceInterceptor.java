package com.cangzhitao.jbf.core.interceptor;

import java.lang.reflect.Method;

import javax.transaction.Transactional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cangzhitao.jbf.core.annotation.CacheService;
import com.cangzhitao.jbf.core.annotation.NoCache;
import com.cangzhitao.jbf.core.util.JsonUtil;

@Component
@Aspect
@Order(1)
public class CacheServiceInterceptor {
	
	@Autowired
	private CacheManager cacheManager;
	
	private static final String CACHE_PREFIX = "CacheService_";

	@Around("execution(* com.cangzhitao.jbf.core.service.BaseService.*(..))")
	public Object process(ProceedingJoinPoint thisJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
		Method method = methodSignature.getMethod();
		NoCache noCache = method.getAnnotation(NoCache.class);
		if(noCache!=null) {
			return thisJoinPoint.proceed();
		}
		Object target = thisJoinPoint.getTarget();
		CacheService cacheService = target.getClass().getAnnotation(CacheService.class);
		if (cacheService==null) {
			return thisJoinPoint.proceed();
		}
		String[] cacheNames = cacheService.cacheNames();
		if(cacheNames==null||cacheNames.length==0) {
			cacheNames = new String[]{CACHE_PREFIX+target.getClass().getName()};
		}
		//根据是否有事务判断是查询操作还是更新操作
		Transactional transactional = method.getAnnotation(Transactional.class);
		boolean query = true;
		if(transactional!=null) {
			query = false;
		}
		if(query) {
			String key = method.getName()+"#"+JsonUtil.toJSONString(thisJoinPoint.getArgs());
			ValueWrapper valueWrapper = cacheManager.getCache(cacheNames[0]).get(key);
			if(valueWrapper!=null) {
				return valueWrapper.get();
			}
			Object object = thisJoinPoint.proceed();
			for(int i=0;i<cacheNames.length;i++) {
				cacheManager.getCache(cacheNames[i]).put(key, object);
			}
			return object;
		} else {
			for(int i=0;i<cacheNames.length;i++) {
				cacheManager.getCache(cacheNames[i]).clear();
			}
			Object object = thisJoinPoint.proceed();
			return object;
		}
	}
	
}
