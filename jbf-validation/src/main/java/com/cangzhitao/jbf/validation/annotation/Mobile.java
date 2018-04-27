package com.cangzhitao.jbf.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cangzhitao.jbf.validation.Constraint;
import com.cangzhitao.jbf.validation.groups.DEFAULT;
import com.cangzhitao.jbf.validation.validate.MobileValidator;

@Constraint(validatedBy = MobileValidator.class)
@Target( { java.lang.annotation.ElementType.METHOD,  
    java.lang.annotation.ElementType.FIELD })  
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)  
@Documented  
public @interface Mobile {

	String message() default "非法的手机号码"; 
  
	String regexp() default "[\\d]{11}";
	
	boolean empty() default false;
	
	Class<?>[] groups() default {DEFAULT.class};  
	
}


