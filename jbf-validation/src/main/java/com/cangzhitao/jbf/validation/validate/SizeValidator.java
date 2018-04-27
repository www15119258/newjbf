package com.cangzhitao.jbf.validation.validate;

import java.util.HashMap;
import java.util.Map;

import com.cangzhitao.jbf.validation.ConstraintValidator;
import com.cangzhitao.jbf.validation.annotation.Size;

public class SizeValidator implements ConstraintValidator<Size, String> {

	@Override
	public boolean isValid(Object constraintAnnotation, Object value) {
		Size size = (Size) constraintAnnotation;
		if(value==null&&size.empty()) {
			return true;
		}
		if(size.min()==0 && value==null) {
			return true;
		}
		if(!(value instanceof String)) {
			throw new RuntimeException("Size不能用在非String类型的字段上！");
		}
		if(value.toString().length()<size.min()||value.toString().length()>size.max()) {
			return false;
		}
		return true;
	}

	@Override
	public String error(Object constraintAnnotation) {
		Size size = (Size) constraintAnnotation;
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("min", size.min());
		context.put("max", size.max());
		String message = size.message();
		for(Map.Entry<String, Object> entry : context.entrySet()) {
		    String regex = "\\{" + entry.getKey() + "\\}";
		    message = message.replaceAll(regex, entry.getValue().toString());
		}
		return message;
	}


}
