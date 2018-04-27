package com.cangzhitao.jbf.config.controller;

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

import com.cangzhitao.jbf.config.entities.SysOption;
import com.cangzhitao.jbf.config.service.ISysOptionService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller
@RequestMapping(value={"/${sys.path}/config/sysOption"})
public class SysOptionController extends BaseController {
	
	@Autowired
	private ISysOptionService sysOptionService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysOption sysOption) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysOption> page = sysOptionService.findAll(sysOption, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysOption sysOption = sysOptionService.findById(id);
		if(sysOption!=null) {
			resultBean.setData(sysOption);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.option.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysOption sysOption) {
		ResultBean resultBean = new ResultBean();
		sysOption.setCreateInfo();
		validate(sysOption.validate());
		SysOption example = new SysOption().setNull().setProperty("type", sysOption.getType()).setProperty("name", sysOption.getName()).setProperty("deleteFlag", false);
		example = sysOptionService.findOne(example);
		if(example!=null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("同类型下已存在相同参数名！");
			return resultBean;
		}
		sysOption = sysOptionService.save(sysOption);
		resultBean.setData(sysOption);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.option.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysOption sysOption) {
		ResultBean resultBean = new ResultBean();
		if(sysOption.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysOption old = sysOptionService.findById(sysOption.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(sysOption.getName());
		old.setType(sysOption.getType());
		old.setDescription(sysOption.getDescription());
		old.setValue(sysOption.getValue());
		old.setUpdateInfo();
		validate(old.validate());
		SysOption example = new SysOption().setNull().setProperty("type", sysOption.getType()).setProperty("name", sysOption.getName()).setProperty("deleteFlag", false);
		example = sysOptionService.findOne(example);
		if(example!=null&&example.getId()!=sysOption.getId()) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("同类型下已存在相同参数名！");
			return resultBean;
		}
		sysOption = sysOptionService.save(old);
		resultBean.setData(sysOption);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.config.option.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysOption sysOption = sysOptionService.findById(id);
		if(sysOption!=null) {
			sysOptionService.deleteById(id);
			resultBean.setData(sysOption);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
