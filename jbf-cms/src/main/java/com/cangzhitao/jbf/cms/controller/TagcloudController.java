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

import com.cangzhitao.jbf.cms.domain.Tagcloud;
import com.cangzhitao.jbf.cms.service.ITagcloudService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/tagcloud")  
public class TagcloudController extends BaseController {
	
	@Autowired
	private ITagcloudService tagcloudService;

	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Tagcloud tagcloud) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Tagcloud> page = tagcloudService.findAll(tagcloud, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Tagcloud tagcloud = tagcloudService.findById(id);
		if(tagcloud!=null) {
			resultBean.setData(tagcloud);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.tagcloud.save")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Tagcloud tagcloud) {
		ResultBean resultBean = new ResultBean();
		tagcloud.setCreateInfo();
		validate(tagcloud.validate());
		tagcloud = tagcloudService.save(tagcloud);
		resultBean.setData(tagcloud);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.tagcloud.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Tagcloud tagcloud) {
		ResultBean resultBean = new ResultBean();
		if(tagcloud.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Tagcloud old = tagcloudService.findById(tagcloud.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(tagcloud.getName());
		old.setLastUse(tagcloud.getLastUse());
		old.setRefers(tagcloud.getRefers());
		old.setUpdateInfo();
		validate(old.validate());
		tagcloud = tagcloudService.save(old);
		resultBean.setData(tagcloud);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.tagcloud.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Tagcloud tagcloud = tagcloudService.findById(id);
		if(tagcloud!=null) {
			tagcloud.setLogicDelete();
			tagcloudService.save(tagcloud);
			resultBean.setData(tagcloud);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
