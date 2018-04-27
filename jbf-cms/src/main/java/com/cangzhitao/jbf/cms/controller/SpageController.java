package com.cangzhitao.jbf.cms.controller;

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

import com.cangzhitao.jbf.cms.domain.Spage;
import com.cangzhitao.jbf.cms.service.ISpageService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/spage")  
public class SpageController extends BaseController {
	
	@Autowired
	private ISpageService spageService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Spage spage) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Spage> page = spageService.findAll(spage, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Spage spage = spageService.findById(id);
		if(spage!=null) {
			resultBean.setData(spage);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.spage.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Spage spage) {
		ResultBean resultBean = new ResultBean();
		spage.setCreateInfo();
		validate(spage.validate());
		spage = spageService.save(spage);
		resultBean.setData(spage);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.spage.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Spage spage) {
		ResultBean resultBean = new ResultBean();
		if(spage.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Spage old = spageService.findById(spage.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setTitle(spage.getTitle());
		old.setSummary(spage.getSummary());
		old.setContent(spage.getContent());
		old.setFixedLink(spage.getFixedLink());
		old.setPic(spage.getPic());
		old.setTemplate(spage.getTemplate());
		old.setCommentEnable(spage.getCommentEnable());
		old.setCommentShow(spage.getCommentShow());
		old.setViewCount(spage.getViewCount());
		old.setCommentCount(spage.getCommentCount());
		old.setSupportCount(spage.getSupportCount());
		old.setCollectionCount(spage.getCollectionCount());
		old.setStatus(spage.getStatus());
		old.setUpdateInfo();
		validate(old.validate());
		spage = spageService.save(old);
		resultBean.setData(spage);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.spage.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Spage spage = spageService.findById(id);
		if(spage!=null) {
			spage.setLogicDelete();
			spageService.save(spage);
			resultBean.setData(spage);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
