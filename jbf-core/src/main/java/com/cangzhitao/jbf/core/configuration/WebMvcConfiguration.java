package com.cangzhitao.jbf.core.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cangzhitao.jbf.core.converter.UniversalEnumConverterFactory;
import com.cangzhitao.jbf.core.interceptor.RequestResponseInterceptor;
import com.cangzhitao.jbf.core.serializer.PageRequestSerializer;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
	
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new RequestResponseInterceptor()).addPathPatterns("/**");
	}

	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        fastJsonConfig.setSerializerFeatures(SerializerFeature.BrowserSecure, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteDateUseDateFormat);
        //对分页进行自定义序列号，避免缓存key重复
        fastJsonConfig.getSerializeConfig().put(PageRequest.class, new PageRequestSerializer());
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
	
	public void addFormatters(FormatterRegistry registry) {  
        registry.addConverterFactory(new UniversalEnumConverterFactory());  
    }
	
	@Bean
    public FilterRegistrationBean<HttpPutFormContentFilter> httpPutFormContentFilter() {
        FilterRegistrationBean<HttpPutFormContentFilter> registration = new FilterRegistrationBean<HttpPutFormContentFilter>(new HttpPutFormContentFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
	
	/*public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedOrigins("*");
	}*/
	
	/*@Bean
    public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("x-auth-token");
        config.addExposedHeader("x-total-count");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }*/
	
}
