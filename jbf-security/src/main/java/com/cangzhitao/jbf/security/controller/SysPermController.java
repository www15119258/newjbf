package com.cangzhitao.jbf.security.controller;

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

import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysPerm;
import com.cangzhitao.jbf.security.service.ISysPermService;

@Controller 
@RequestMapping("/${sys.path}/security/perm")  
public class SysPermController extends BaseController {
	
	@Autowired
	private ISysPermService sysPermService;

	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(SysPerm sysPerm) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<SysPerm> page = sysPermService.findAll(sysPerm, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysPerm sysPerm = sysPermService.findById(id);
		if(sysPerm!=null) {
			resultBean.setData(sysPerm);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(SysPerm sysPerm) {
		ResultBean resultBean = new ResultBean();
		sysPerm.setCreateInfo();
		validate(sysPerm.validate());
		sysPerm = sysPermService.save(sysPerm);
		resultBean.setData(sysPerm);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(SysPerm sysPerm) {
		ResultBean resultBean = new ResultBean();
		if(sysPerm.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		SysPerm old = sysPermService.findById(sysPerm.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(sysPerm.getName());
		old.setPerm(sysPerm.getPerm());
		old.setDescription(sysPerm.getDescription());
		old.setStatus(sysPerm.getStatus());
		old.setUpdateInfo();
		validate(old.validate());
		sysPerm = sysPermService.save(old);
		resultBean.setData(sysPerm);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.security.module.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		SysPerm sysPerm = sysPermService.findById(id);
		if(sysPerm!=null) {
			sysPerm.setLogicDelete();
			sysPermService.save(sysPerm);
			resultBean.setData(sysPerm);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
