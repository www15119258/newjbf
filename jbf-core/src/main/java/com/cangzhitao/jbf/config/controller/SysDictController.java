package com.cangzhitao.jbf.config.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cangzhitao.jbf.config.entities.SysDict;
import com.cangzhitao.jbf.config.service.ISysDictService;
import com.cangzhitao.jbf.config.util.SysDictUtil;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller
@RequestMapping(value={"/${sys.path}/config/sysDict"})
public class SysDictController extends BaseController {
	
	@Autowired
	private ISysDictService sysDictService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysDict sysDict) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysDict> page = sysDictService.findAll(sysDict, pageable);
		if(page.getTotalElements()>0) {
			List<SysDict> list = page.getContent();
			for(int i=0;i<list.size();i++) {
				SysDict e = list.get(i);
				if(e.getParent()!=null) {
					SysDict parent = sysDictService.findById(e.getParent());
					if(parent!=null) {
						e.setParentName(parent.getLabel());
					}
				}
			}
		}
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryListByType/{type}"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryListByType(@PathVariable String type) {
		if(type==null||"".equals(type)) {
			return new ArrayList<SysDict>();
		}
		List<SysDict> dicts = SysDictUtil.getSysDictList(type);
		return dicts;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryTypes"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryTypes() {
		List<String> typeList = sysDictService.getTypeList();
		return typeList;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysDict sysDict = sysDictService.findById(id);
		if(sysDict!=null) {
			resultBean.setData(sysDict);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.dict.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysDict sysDict) {
		ResultBean resultBean = new ResultBean();
		sysDict.setCreateInfo();
		validate(sysDict.validate());
		sysDict = sysDictService.save(sysDict);
		resultBean.setData(sysDict);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.dict.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysDict sysDict) {
		ResultBean resultBean = new ResultBean();
		if(sysDict.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysDict old = sysDictService.findById(sysDict.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setType(sysDict.getType());
		old.setValue(sysDict.getValue());
		old.setLabel(sysDict.getLabel());
		old.setDescription(sysDict.getDescription());
		old.setVisible(sysDict.getVisible());
		old.setParent(sysDict.getParent());
		old.setUpdateInfo();
		validate(old.validate());
		sysDict = sysDictService.save(old);
		resultBean.setData(sysDict);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.dict.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysDict sysDict = sysDictService.findById(id);
		if(sysDict!=null) {
			sysDictService.deleteById(id);
			resultBean.setData(sysDict);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
