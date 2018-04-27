package com.cangzhitao.jbf.validation;

import java.lang.annotation.Annotation;

public interface ConstraintValidator<A extends Annotation, T> {

	boolean isValid(Object constraintAnnotation, Object value);
	
	String error(Object constraintAnnotation);
	
}
