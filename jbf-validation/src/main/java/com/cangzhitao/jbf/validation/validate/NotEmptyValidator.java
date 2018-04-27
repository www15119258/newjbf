package com.cangzhitao.jbf.validation.validate;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		if(value==null) {
			return false;
		}
		if(!(value instanceof String)) {
			throw new RuntimeException("NotEmpty不能用在非String类型的字段上！");
		}
		if(value.toString().length()==0) {
			return false;
		}
		return true;
	}

	@Override
	public String error(Object constraintAnnotation) {
		NotEmpty notEmpty = (NotEmpty) constraintAnnotation;
		return notEmpty.message();
	}


}
