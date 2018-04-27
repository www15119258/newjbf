package com.cangzhitao.jbf.config.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cangzhitao.jbf.config.entities.SysOption;
import com.cangzhitao.jbf.config.repository.SysOptionRepository;
import com.cangzhitao.jbf.core.repository.base.BaseRepository;
import com.cangzhitao.jbf.core.service.BaseService;

@Service
public class SysOptionService extends BaseService<SysOption, Long> implements ISysOptionService {

	@Autowired
	private SysOptionRepository sysOptionRepository; 
	
	@Override
	public BaseRepository<SysOption, Long> getRepository() {
		return sysOptionRepository;
	}
	
	@Override
	public List<String> getTypeList() {
		String sql = "select t.type from t_sys_option t group by t.type order by t.type";
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<mapList.size();i++) {
			Map<String, Object> map = mapList.get(i);
			list.add(map.get("type")+"");
		}
		return list;
	}

}
