package com.cangzhitao.jbf.config.util;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.config.service.ISysDictService;
import com.cangzhitao.jbf.core.enums.Visible;
import com.cangzhitao.jbf.core.util.SpringUtil;

public class SysDictUtil {

	public static List<SysDict> getSysDictList(String type) {
		SysDict example = new SysDict().setNull().setProperty("type", type).setProperty("deleteFlag", false).setProperty("visible", Visible.TRUE);
		Sort sort = new Sort(Direction.ASC, "sort");
		return getSysDictService().findAll(example, sort);
	}
	
	public static SysDict getSysDictByTypeAndValue(String type, String value) {
		SysDict example = new SysDict().setNull().setProperty("type", type).setProperty("value", value);
		return getSysDictService().findOne(example);
	}
	
	public static String getLabelByTypeAndValue(String type, String value) {
		SysDict example = getSysDictByTypeAndValue(type, value);
		if(example==null) {
			return null;
		}
		return example.getLabel();
	}
	
	public static List<SysDict> getSysDictByTypeAndLabel(String type, String label) {
		SysDict example = new SysDict().setNull().setProperty("type", type).setProperty("label", label);
		Sort sort = new Sort(Direction.ASC, "sort");
		return getSysDictService().findAll(example, sort);
	}
	
	public static SysDict getOneSysDictByTypeAndLabel(String type, String label) {
		SysDict example = new SysDict().setNull().setProperty("type", type).setProperty("label", label);
		return getSysDictService().findOne(example);
	}
	
	public static String getOneVauleByTypeAndLabel(String type, String label) {
		SysDict example = getOneSysDictByTypeAndLabel(type, label);
		if(example==null) {
			return null;
		}
		return example.getValue();
	}
	
	public static List<String> getTypeList() {
		return getSysDictService().getTypeList();
	}
	
	public static ISysDictService getSysDictService() {
		return SpringUtil.getBean(ISysDictService.class);
	}
	
}
