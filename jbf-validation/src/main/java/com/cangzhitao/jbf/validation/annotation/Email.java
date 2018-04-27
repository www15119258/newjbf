package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.EmailValidator;

@Constraint(validatedBy = EmailValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface Email {

	String message() default "非法的Email地址"; 
  
	String regexp() default "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
	
	boolean empty() default false;
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


