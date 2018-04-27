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

import com.cangzhitao.jbf.cms.domain.Aggregation;
import com.cangzhitao.jbf.cms.domain.Category;
import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.cms.domain.Spage;
import com.cangzhitao.jbf.cms.enmus.AggregationType;
import com.cangzhitao.jbf.cms.service.IAggregationService;
import com.cangzhitao.jbf.cms.service.ICategoryService;
import com.cangzhitao.jbf.cms.service.IPostService;
import com.cangzhitao.jbf.cms.service.ISpageService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;

@Controller 
@RequestMapping("/${sys.path}/cms/aggregation")  
public class AggregationController extends BaseController {
	
	@Autowired
	private IAggregationService aggregationService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private ISpageService spageService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Aggregation aggregation) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Aggregation> page = aggregationService.findAll(aggregation, pageable);
		if(page.getContent()!=null&&page.getContent().size()>0) {
			List<Aggregation> contents = page.getContent();
			for(int i=0;i<contents.size();i++) {
				Aggregation content = contents.get(i);
				if(content.getAggregationType() == AggregationType.POST) {
					if(content.getRefer()!=null) {
						Post post = postService.findById(content.getRefer());
						content.setPost(post);
					}
				} else if(content.getAggregationType() == AggregationType.SPAGE) {
					if(content.getRefer()!=null) {
						Spage spage = spageService.findById(content.getRefer());
						content.setSpage(spage);
					}
				} else if(content.getAggregationType() == AggregationType.CATEGORY) {
					if(content.getRefer()!=null) {
						Category category = categoryService.findById(content.getRefer());
						content.setCategory(category);
					}
				}
			}
		}
		resultBean.setData(page);
		return resultBean;
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Aggregation aggregation = aggregationService.findById(id);
		if(aggregation!=null) {
			if(aggregation.getAggregationType() == AggregationType.POST) {
				if(aggregation.getRefer()!=null) {
					Post post = postService.findById(aggregation.getRefer());
					aggregation.setPost(post);
				}
			} else if(aggregation.getAggregationType() == AggregationType.SPAGE) {
				if(aggregation.getRefer()!=null) {
					Spage spage = spageService.findById(aggregation.getRefer());
					aggregation.setSpage(spage);
				}
			} else if(aggregation.getAggregationType() == AggregationType.CATEGORY) {
				if(aggregation.getRefer()!=null) {
					Category category = categoryService.findById(aggregation.getRefer());
					aggregation.setCategory(category);
				}
			}
			resultBean.setData(aggregation);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.aggregation.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Aggregation aggregation) {
		ResultBean resultBean = new ResultBean();
		aggregation.setCreateInfo();
		validate(aggregation.validate());
		aggregation = aggregationService.save(aggregation);
		resultBean.setData(aggregation);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.aggregation.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Aggregation aggregation) {
		ResultBean resultBean = new ResultBean();
		if(aggregation.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Aggregation old = aggregationService.findById(aggregation.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setName(aggregation.getName());
		old.setDescription(aggregation.getDescription());
		old.setType(aggregation.getType());
		old.setLogo(aggregation.getLogo());
		old.setLink(aggregation.getLink());
		old.setTarget(aggregation.getTarget());
		old.setStatus(aggregation.getStatus());
		old.setSort(aggregation.getSort());
		old.setAggregationType(aggregation.getAggregationType());
		old.setRefer(aggregation.getRefer());
		old.setUpdateInfo();
		validate(old.validate());
		aggregation = aggregationService.save(old);
		resultBean.setData(aggregation);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.aggregation.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Aggregation aggregation = aggregationService.findById(id);
		if(aggregation!=null) {
			aggregation.setLogicDelete();
			aggregationService.save(aggregation);
			resultBean.setData(aggregation);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
