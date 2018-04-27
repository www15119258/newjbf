package com.cangzhitao.jbf.cms.controller;

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

import com.cangzhitao.jbf.cms.domain.Category;
import com.cangzhitao.jbf.cms.service.ICategoryService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private ICategoryService categoryService;
	
	@RequiresAuthentication
	@RequestMapping(value={"queryTree"}, method={RequestMethod.GET})
	@ResponseBody
	public Object queryTree() {
		Category category = new Category().setNull().setProperty("deleteFlag", false);
		List<Category> tree = categoryService.findFullTree(category);
		return tree;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Category category) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Category> page = categoryService.findAll(category, pageable);
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryList"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryList() {
		ResultBean resultBean = new ResultBean();
		Category category = new Category().setNull().setProperty("deletaFlag", false);
		List<Category> list = categoryService.findAll(category);
		resultBean.setData(list);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Category category = categoryService.findById(id);
		if(category!=null) {
			resultBean.setData(category);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.category.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Category category) {
		ResultBean resultBean = new ResultBean();
		category.setCreateInfo();
		validate(category.validate());
		category = categoryService.save(category);
		resultBean.setData(category);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.category.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Category category) {
		ResultBean resultBean = new ResultBean();
		if(category.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Category old = categoryService.findById(category.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(category.getName());
		old.setUrl(category.getUrl());
		old.setDescription(category.getDescription());
		old.setParent(category.getParent());
		old.setUpdateInfo();
		validate(old.validate());
		category = categoryService.save(old);
		resultBean.setData(category);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.category.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Category category = categoryService.findById(id);
		if(category!=null) {
			List<Category> children = categoryService.findTreeAllChildren(category.getId());
			children.add(category);
			for(int i=0;i<children.size();i++) {
				children.get(i).setLogicDelete();
			}
			categoryService.save(children);
			resultBean.setData(children);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
