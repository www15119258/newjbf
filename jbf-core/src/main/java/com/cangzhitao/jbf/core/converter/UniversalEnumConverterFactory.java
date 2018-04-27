package com.cangzhitao.jbf.core.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public class UniversalEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
	
	private static final ConcurrentMap<Class<BaseEnum>, Converter<String,BaseEnum>> converterMap = new ConcurrentHashMap<Class<BaseEnum>, Converter<String, BaseEnum>>();
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
		if(targetType.getClass().isAssignableFrom(BaseEnum.class.getClass())) {
			Converter<String, T> result = (Converter<String, T>) converterMap.get(targetType);  
	        if(result == null) {  
	            result = new StringToEnum<T>(targetType);  
	            converterMap.put((Class<BaseEnum>)targetType, (Converter<String,BaseEnum>)result);  
	        }  
	        return result;  
		}
		return null;
	}  
  
    class StringToEnum<T extends BaseEnum> implements Converter<String, T> {  
        private Map<String, T> enumMap = new HashMap<>();  
  
        public StringToEnum(Class<T> enumType) {  
            T[] enums = enumType.getEnumConstants();  
            for(T e : enums) {  
                enumMap.put(e.value(), e);  
            }  
        }  
  
        @Override  
        public T convert(String source) {  
            T result = enumMap.get(source);  
            if(result == null) {  
                throw new IllegalArgumentException("No element matches " + source);  
            }  
            return result;  
        }  
    }




	
	
	

}
