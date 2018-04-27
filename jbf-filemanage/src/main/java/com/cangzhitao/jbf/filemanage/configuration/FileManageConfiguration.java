package com.cangzhitao.jbf.filemanage.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cangzhitao.jbf.core.util.PropertiesUtil;

@Configuration
public class FileManageConfiguration {

	@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize(PropertiesUtil.getProperty("filemanage", "upload.maxsize", "3MB"));  
        factory.setMaxRequestSize(PropertiesUtil.getProperty("filemanage", "upload.maxsize", "3MB"));  
        return factory.createMultipartConfig();  
    }
	
}
