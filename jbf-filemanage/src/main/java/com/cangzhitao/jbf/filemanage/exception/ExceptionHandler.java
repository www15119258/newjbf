package com.cangzhitao.jbf.filemanage.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cangzhitao.jbf.core.util.PropertiesUtil;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if(ex instanceof MaxUploadSizeExceededException) {
        	response.setStatus(500);
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().print("超过允许的文件大小("+PropertiesUtil.getProperty("filemanage", "upload.maxsize")+")！");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
			}
			return null;
        }
		return null;
	}
	

}
