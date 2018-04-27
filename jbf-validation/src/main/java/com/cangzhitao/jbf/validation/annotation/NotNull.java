package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.NotNullValidator;

@Constraint(validatedBy = NotNullValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface NotNull {

	String message() default "不能为空！"; 
  
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


