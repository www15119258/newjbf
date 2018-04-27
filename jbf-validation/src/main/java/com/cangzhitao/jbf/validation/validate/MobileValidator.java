package com.cangzhitao.jbf.validation.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.Mobile;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		Mobile mobile = (Mobile) constraintAnnotation;
		if((value==null||value.toString().equals(""))&&mobile.empty()) {
			return true;
		}
		Pattern p = Pattern.compile(mobile.regexp());
        Matcher m = p.matcher(value.toString());
        return m.matches();
	}

	@Override
	public String error(Object constraintAnnotation) {
		Mobile mobile = (Mobile) constraintAnnotation;
		return mobile.message();
	}

}
