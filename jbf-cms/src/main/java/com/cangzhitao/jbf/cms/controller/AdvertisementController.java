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

import com.cangzhitao.jbf.cms.domain.Advertisement;
import com.cangzhitao.jbf.cms.service.IAdvertisementService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/advertisement")  
public class AdvertisementController extends BaseController {
	
	@Autowired
	private IAdvertisementService advertisementService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Advertisement advertisement) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Advertisement> page = advertisementService.findAll(advertisement, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Advertisement advertisement = advertisementService.findById(id);
		if(advertisement!=null) {
			resultBean.setData(advertisement);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.advertisement.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Advertisement advertisement) {
		ResultBean resultBean = new ResultBean();
		advertisement.setCreateInfo();
		validate(advertisement.validate());
		advertisement = advertisementService.save(advertisement);
		resultBean.setData(advertisement);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.advertisement.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Advertisement advertisement) {
		ResultBean resultBean = new ResultBean();
		if(advertisement.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Advertisement old = advertisementService.findById(advertisement.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(advertisement.getName());
		old.setDescription(advertisement.getDescription());
		old.setType(advertisement.getType());
		old.setLogo(advertisement.getLogo());
		old.setLink(advertisement.getLink());
		old.setTarget(advertisement.getTarget());
		old.setStatus(advertisement.getStatus());
		old.setSort(advertisement.getSort());
		old.setUpdateInfo();
		validate(old.validate());
		advertisement = advertisementService.save(old);
		resultBean.setData(advertisement);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.advertisement.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Advertisement advertisement = advertisementService.findById(id);
		if(advertisement!=null) {
			advertisement.setLogicDelete();
			advertisementService.save(advertisement);
			resultBean.setData(advertisement);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
