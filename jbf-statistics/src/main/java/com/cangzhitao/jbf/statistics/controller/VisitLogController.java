package com.cangzhitao.jbf.statistics.controller;

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
import com.cangzhitao.jbf.statistics.entities.VisitLog;
import com.cangzhitao.jbf.statistics.service.IVisitLogService;

@Controller 
@RequestMapping("/${sys.path}/statistics/visitLog")
public class VisitLogController extends BaseController {

	@Autowired
	private IVisitLogService visitLogService;
	
	@RequiresPermissions(value="jbf.statistics.visitLog.view")
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(VisitLog visitLog) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<VisitLog> page = visitLogService.findAll(visitLog, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.statistics.visitLog.view")
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		VisitLog visitLog = visitLogService.findById(id);
		if(visitLog!=null) {
			resultBean.setData(visitLog);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	/*@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(VisitLog visitLog) {
		ResultBean resultBean = new ResultBean();
		visitLog.setCreateInfo();
		validate(visitLog.validate());
		visitLog = visitLogService.save(visitLog);
		resultBean.setData(visitLog);
		return resultBean;
	}
	
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(VisitLog visitLog) {
		ResultBean resultBean = new ResultBean();
		if(visitLog.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		VisitLog old = visitLogService.findById(visitLog.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setUrl(visitLog.getUrl());
		old.setPagetitle(visitLog.getPagetitle());
		old.setReffer(visitLog.getReffer());
		old.setIp(visitLog.getIp());;
		old.setBrowser(visitLog.getBrowser());
		old.setDescription(visitLog.getDescription());
		old.setCountry(visitLog.getCountry());
		old.setArea(visitLog.getArea());
		old.setProvince(visitLog.getProvince());
		old.setCity(visitLog.getCity());
		old.setIsp(visitLog.getIsp());
		old.setVisitor(visitLog.getVisitor());
		old.setUpdateInfo();
		validate(old.validate());
		visitLog = visitLogService.save(old);
		resultBean.setData(visitLog);
		return resultBean;
	}*/
	
	@RequiresPermissions(value="jbf.statistics.visitLog.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		VisitLog visitLog = visitLogService.findById(id);
		if(visitLog!=null) {
			visitLog.setLogicDelete();
			visitLogService.save(visitLog);
			resultBean.setData(visitLog);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
}
