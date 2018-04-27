package com.cangzhitao.jbf.config.util;

import java.util.List;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.config.entities.SysOption;
import com.cangzhitao.jbf.config.service.ISysOptionService;
import com.cangzhitao.jbf.core.util.SpringUtil;

public class SysOptionUtil {

	public static String getValue(String type, String name) {
		return getStringValue(type, name);
	}
	
	public static String getStringValue(String type, String name) {
		SysOption option = getOption(type, name);
		if(option!=null) {
			return option.getValue();
		}
		return null;
	}
	
	public static Integer getIntegerValue(String type, String name) {
		String value = getStringValue(type, name);
		if(value==null) {
			return null;
		}
		return Integer.parseInt(value);
	}
	
	public static Long getLongValue(String type, String name) {
		String value = getStringValue(type, name);
		if(value==null) {
			return null;
		}
		return Long.parseLong(value);
	}
	
	public static Boolean getBooleanValue(String type, String name) {
		String value = getStringValue(type, name);
		if(value==null) {
			return null;
		}
		if("1".equals(value)||"true".equalsIgnoreCase(value)) {
			return true;
		}
		return false;
	}
	
	public static SysOption getOption(String type, String name) {
		SysOption example = new SysOption().setNull().setProperty("type", type).setProperty("name", name).setProperty("deleteFlag", false);
		example = getSysOptionService().findOne(example);
		return example;
	}
	
	public static List<SysOption> getSysOptionList(String type) {
		SysOption example = new SysDict().setNull().setProperty("type", type).setProperty("deleteFlag", false);
		return getSysOptionService().findAll(example);
	}
	
	public static List<String> getTypeList() {
		return getSysOptionService().getTypeList();
	}
	
	public static ISysOptionService getSysOptionService() {
		return SpringUtil.getBean(ISysOptionService.class);
	}
	
}
