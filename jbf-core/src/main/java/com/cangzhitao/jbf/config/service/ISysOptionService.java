package com.cangzhitao.jbf.config.service;

import java.util.List;

import com.cangzhitao.jbf.config.entities.SysOption;
import com.cangzhitao.jbf.core.service.base.IBaseService;

public interface ISysOptionService extends IBaseService<SysOption, Long> {
	
	public List<String> getTypeList();
	
}
