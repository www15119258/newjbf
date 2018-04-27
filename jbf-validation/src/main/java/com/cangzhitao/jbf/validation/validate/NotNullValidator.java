package com.cangzhitao.jbf.validation.validate;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.NotNull;

public class NotNullValidator implements ConstraintValidator<NotNull, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		if(value==null) {
			return false;
		}
		return true;
	}

	@Override
	public String error(Object constraintAnnotation) {
		NotNull notNull = (NotNull) constraintAnnotation;
		return notNull.message();
	}


}
