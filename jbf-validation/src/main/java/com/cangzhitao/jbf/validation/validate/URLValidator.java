package com.cangzhitao.jbf.validation.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.URL;

public class URLValidator implements ConstraintValidator<URL, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		URL url = (URL) constraintAnnotation;
		if((value==null||value.toString().equals(""))&&url.empty()) {
			return true;
		}
		Pattern p = Pattern.compile(url.regexp());
        Matcher m = p.matcher(value.toString());
        return m.matches();
	}

	@Override
	public String error(Object constraintAnnotation) {
		URL url = (URL) constraintAnnotation;
		return url.message();
	}

}
