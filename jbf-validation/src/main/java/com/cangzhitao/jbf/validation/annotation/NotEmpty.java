package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.NotEmptyValidator;

@Constraint(validatedBy = NotEmptyValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface NotEmpty {

	String message() default "不能为空！"; 
  
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


