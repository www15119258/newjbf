package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.SizeValidator;

@Constraint(validatedBy = SizeValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface Size {

	String message() default "长度必须在{min}和{max}之间"; 
  
	int min();
	
	int max();
	
	boolean empty() default false;
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


