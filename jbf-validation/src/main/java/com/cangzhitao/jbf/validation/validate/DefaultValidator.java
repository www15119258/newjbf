package com.cangzhitao.jbf.validation.validate;

import java.lang.annotation.Annotation;

import com.cangzhitao.jbf.validation.ConstraintValidator;

public class DefaultValidator implements ConstraintValidator<Annotation, Object> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		return true;
	}


	@Override
	public String error(Object constraintAnnotation) {
		return "";
	}

}
