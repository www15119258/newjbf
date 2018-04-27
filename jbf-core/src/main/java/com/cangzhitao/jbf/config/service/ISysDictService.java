package com.cangzhitao.jbf.config.service;

import java.util.List;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.core.service.base.IBaseService;

public interface ISysDictService extends IBaseService<SysDict, Long> {
	
	public List<String> getTypeList();

}
