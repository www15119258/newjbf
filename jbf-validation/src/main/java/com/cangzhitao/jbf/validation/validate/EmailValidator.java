package com.cangzhitao.jbf.validation.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		Email email = (Email) constraintAnnotation;
		if((value==null||value.toString().equals(""))&&email.empty()) {
			return true;
		}
		Pattern p = Pattern.compile(email.regexp());
        Matcher m = p.matcher(value.toString());
        return m.matches();
	}

	@Override
	public String error(Object constraintAnnotation) {
		Email email = (Email) constraintAnnotation;
		return email.message();
	}

}
