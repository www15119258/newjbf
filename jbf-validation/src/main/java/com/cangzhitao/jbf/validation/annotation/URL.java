package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.URLValidator;

@Constraint(validatedBy = URLValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface URL {

	String message() default "非法的URL地址"; 
  
	String regexp() default "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";
	
	boolean empty() default false;
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


