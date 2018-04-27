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

import com.cangzhitao.jbf.cms.domain.FriendLink;
import com.cangzhitao.jbf.cms.service.IFriendLinkService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/friendLink")  
public class FriendLinkController extends BaseController {
	
	@Autowired
	private IFriendLinkService friendLinkService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(FriendLink friendLink) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<FriendLink> page = friendLinkService.findAll(friendLink, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		FriendLink friendLink = friendLinkService.findById(id);
		if(friendLink!=null) {
			resultBean.setData(friendLink);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.friendLink.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(FriendLink friendLink) {
		ResultBean resultBean = new ResultBean();
		friendLink.setCreateInfo();
		validate(friendLink.validate());
		friendLink = friendLinkService.save(friendLink);
		resultBean.setData(friendLink);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.friendLink.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(FriendLink friendLink) {
		ResultBean resultBean = new ResultBean();
		if(friendLink.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		FriendLink old = friendLinkService.findById(friendLink.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(friendLink.getName());
		old.setDescription(friendLink.getDescription());
		old.setType(friendLink.getType());
		old.setLogo(friendLink.getLogo());
		old.setLink(friendLink.getLink());
		old.setTarget(friendLink.getTarget());
		old.setStatus(friendLink.getStatus());
		old.setSort(friendLink.getSort());
		old.setUpdateInfo();
		validate(old.validate());
		friendLink = friendLinkService.save(old);
		resultBean.setData(friendLink);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.friendLink.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		FriendLink friendLink = friendLinkService.findById(id);
		if(friendLink!=null) {
			friendLink.setLogicDelete();
			friendLinkService.save(friendLink);
			resultBean.setData(friendLink);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
