package com.cangzhitao.jbf.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cangzhitao.jbf.validation.annotation.Email;
import com.cangzhitao.jbf.validation.annotation.Mobile;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;
import com.cangzhitao.jbf.validation.validate.DefaultValidator;

public class Validator {
	
	private static final Class<?>[] VALIDATES = new Class<?>[]{
		NotNull.class,
		NotEmpty.class,
		Size.class,
		Email.class,
		Mobile.class
	};
	
	private static Map<Class<?>, Map<Class<?>,List<Map<String, Object>>>> validateMap = new HashMap<Class<?>, Map<Class<?>,List<Map<String, Object>>>>();

	private static Map<Class<?>, Object[]> constraintValidatorMap = new HashMap<Class<?>, Object[]>();
	
	private static ConstraintValidator<? extends Annotation, ?> defaultValidator = new DefaultValidator();
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> validate(Object o, Class<?>... groups) {
		List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
		try {
			for(int i=0;i<groups.length;i++) {
				List<Map<String, Object>> fieldValidates = getFieldValidate(o.getClass(), groups[i]);
				for(int j=0;j<fieldValidates.size();j++) {
					Map<String, Object> map = fieldValidates.get(j);
					Field field = (Field) map.get("field");
					Method method = (Method) map.get("method");
					Object anno = map.get("anno");
					Object value = method.invoke(o);
					Object[] constraintValidators = (Object[]) map.get("validator");
					for(int k=0;k<constraintValidators.length;k++) {
						ConstraintValidator<? extends Annotation, ?> constraintValidator = (ConstraintValidator<? extends Annotation, ?>) constraintValidators[k];
						boolean falg = constraintValidator.isValid(anno, value);
						if(falg==false) {
							Map<String, String> errorMap = new HashMap<String, String>();
							errorMap.put(field.getName(), constraintValidator.error(anno));
							resultMapList.add(errorMap);
						}
					}
				}
			}
			return resultMapList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Map<String, String>>();
	}
	
	private static List<Map<String, Object>> getFieldValidate(Class<?> entityClass, Class<?> group) {
		Map<Class<?>,List<Map<String, Object>>> entityClassMap = validateMap.get(entityClass);
		if(entityClassMap==null) {
			synchronized (Validator.class) {
				entityClassMap = validateMap.get(entityClass);
				if(entityClassMap==null) {
					entityClassMap = new HashMap<Class<?>, List<Map<String,Object>>>();
					validateMap.put(entityClass, entityClassMap);
				}
			}
		}
		List<Map<String, Object>> fieldValidates = entityClassMap.get(group);
		if(fieldValidates==null) {
			synchronized (Validator.class) {
				fieldValidates = entityClassMap.get(group);
				if(fieldValidates==null) {
					fieldValidates = new ArrayList<Map<String, Object>>();
					entityClassMap.put(group, fieldValidates);
					List<Field> fieldList = new ArrayList<Field>();
					Field[] fields = entityClass.getDeclaredFields();
					fieldList.addAll(Arrays.asList(fields));
					Class<?> temp = entityClass;
					while(temp!=Object.class) {
						temp = temp.getSuperclass();
						fields = temp.getDeclaredFields();
						fieldList.addAll(Arrays.asList(fields));
					}
					fields = fieldList.toArray(new Field[]{});
					for(int i=0;i<fields.length;i++) {
						Field field = fields[i];
						if(Modifier.isStatic(field.getModifiers())) {
							continue;
						}
						String methodName = "get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1);
						Method method = null;
						try {
							method = entityClass.getMethod(methodName);
						} catch(Exception e) {
						}
						if(method==null) {
							continue;
						}
						for(int j=0;j<VALIDATES.length;j++) {
							Class<?> cz = VALIDATES[j];
							if(cz == NotNull.class) {
								NotNull anno = field.getAnnotation(NotNull.class);
								if(anno!=null) {
									if(!hasGroup(anno.groups(), group)) {
										continue;
									}
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("method", method);
									map.put("field", field);
									map.put("anno", anno);
									map.put("validator", getConstraintValidator(NotNull.class));
									fieldValidates.add(map);
								}
							} else if(cz == NotEmpty.class) {
								NotEmpty anno = field.getAnnotation(NotEmpty.class);
								if(anno!=null) {
									if(!hasGroup(anno.groups(), group)) {
										continue;
									}
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("method", method);
									map.put("field", field);
									map.put("anno", anno);
									map.put("validator", getConstraintValidator(NotEmpty.class));
									fieldValidates.add(map);
								}
							} else if(cz == Size.class) {
								Size anno = field.getAnnotation(Size.class);
								if(anno!=null) {
									if(!hasGroup(anno.groups(), group)) {
										continue;
									}
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("method", method);
									map.put("field", field);
									map.put("anno", anno);
									map.put("validator", getConstraintValidator(Size.class));
									fieldValidates.add(map);
								}
							} else if(cz == Email.class) {
								Email anno = field.getAnnotation(Email.class);
								if(anno!=null) {
									if(!hasGroup(anno.groups(), group)) {
										continue;
									}
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("method", method);
									map.put("field", field);
									map.put("anno", anno);
									map.put("validator", getConstraintValidator(Email.class));
									fieldValidates.add(map);
								}
							} else if(cz == Mobile.class) {
								Mobile anno = field.getAnnotation(Mobile.class);
								if(anno!=null) {
									if(!hasGroup(anno.groups(), group)) {
										continue;
									}
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("method", method);
									map.put("field", field);
									map.put("anno", anno);
									map.put("validator", getConstraintValidator(Mobile.class));
									fieldValidates.add(map);
								}
							}
						}
					}
					
				}
			}
		}
		return fieldValidates;
	}
	
	@SuppressWarnings({"rawtypes"})
	private static Object[] getConstraintValidator(Class<?> clazz) {
		Object[] constraintValidators = constraintValidatorMap.get(clazz);
		if(constraintValidators==null) {
			synchronized (Validator.class) {
				constraintValidators = constraintValidatorMap.get(clazz);
				if(constraintValidators==null) {
					Constraint constraint = clazz.getAnnotation(Constraint.class);
					List<ConstraintValidator> list = new ArrayList<ConstraintValidator>();
					if(constraint==null) {
						list.add(defaultValidator);
					} else {
						Class[] vclazz = constraint.validatedBy();
						if(vclazz==null||vclazz.length==0) {
							list.add(defaultValidator);
						} else {
							for(int i=0;i<vclazz.length;i++) {
								Class constraintValidatorClass = vclazz[i];
								ConstraintValidator constraintValidator = null;
								try {
									constraintValidator = (ConstraintValidator) constraintValidatorClass.newInstance();
								} catch (Exception e) {
								}
								if(constraintValidator!=null) {
									list.add(constraintValidator);
								}
							}
						}
					}
					constraintValidators = list.toArray();
					constraintValidatorMap.put(clazz, constraintValidators);
				}
			}
		}
		return constraintValidators;
	}
	
	private static boolean hasGroup(Class<?>[] groups, Class<?> group) {
		for(int i=0;i<groups.length;i++) {
			if(groups[i] == group) {
				return true;
			}
		}
		return false;
	}

}
