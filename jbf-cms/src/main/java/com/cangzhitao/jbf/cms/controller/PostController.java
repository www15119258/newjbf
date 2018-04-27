package com.cangzhitao.jbf.cms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.cms.domain.Tagcloud;
import com.cangzhitao.jbf.cms.service.ICategoryService;
import com.cangzhitao.jbf.cms.service.IPostService;
import com.cangzhitao.jbf.cms.service.ITagcloudService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/cms/post")  
public class PostController extends BaseController {
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ITagcloudService tagcloudService;

	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Post post) {
		Long category = post.getCategory();
		if(category==null) {
			ResultBean resultBean = new ResultBean();
			Pageable pageable = PageRequestUtil.getPage(getRequest());
			Page<Post> page = postService.findAll(post, pageable);
			if(page.getContent()!=null&&page.getContent().size()>0) {
				List<Post> posts = page.getContent();
				for(int i=0;i<posts.size();i++) {
					List<Category> categoryList = categoryService.getCategoryByPostId(posts.get(i).getId());
					posts.get(i).setCategoryList(categoryList);
					List<Tagcloud> tagcloudList = tagcloudService.getTagcloudByPostId(posts.get(i).getId());
					posts.get(i).setTagcloudList(tagcloudList);
					SysUser user = UserManager.getUser(posts.get(i).getCreateBy());
					if(user!=null) {
						posts.get(i).setCreator(user.getNickname());
					}
				}
			}
			resultBean.setData(page);
			return resultBean;
		} else {
			ResultBean resultBean = new ResultBean();
			Pageable pageable = PageRequestUtil.getPage(getRequest());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(post.getTitle()!=null&&!"".equals(post.getTitle())) {
				paramMap.put("title", post.getTitle());
			}
			if(post.getSourceFrom()!=null) {
				paramMap.put("sourceFrom", post.getSourceFrom());
			}
			if(post.getIsTop()!=null) {
				paramMap.put("isTop", post.getIsTop());
			}
			if(post.getStatus()!=null) {
				paramMap.put("status", post.getStatus());
			}
			Page<Post> page = postService.findPost(paramMap, category, pageable);
			if(page.getContent()!=null&&page.getContent().size()>0) {
				List<Post> posts = page.getContent();
				for(int i=0;i<posts.size();i++) {
					List<Category> categoryList = categoryService.getCategoryByPostId(posts.get(i).getId());
					posts.get(i).setCategoryList(categoryList);
					List<Tagcloud> tagcloudList = tagcloudService.getTagcloudByPostId(posts.get(i).getId());
					posts.get(i).setTagcloudList(tagcloudList);
					SysUser user = UserManager.getUser(posts.get(i).getCreateBy());
					if(user!=null) {
						posts.get(i).setCreator(user.getNickname());
					}
				}
			}
			resultBean.setData(page);
			return resultBean;
		}
	}
	
	@RequiresAuthentication
	@RequestMapping(value={"queryById/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean queryById(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Post post = postService.findById(id);
		if(post!=null) {
			List<Category> categoryList = categoryService.getCategoryByPostId(post.getId());
			post.setCategoryList(categoryList);
			List<Tagcloud> tagcloudList = tagcloudService.getTagcloudByPostId(post.getId());
			post.setTagcloudList(tagcloudList);
			resultBean.setData(post);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.post.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Post post) {
		ResultBean resultBean = new ResultBean();
		if(post.getPublishDate()==null) {
			post.setPublishDate(new Date());
		}
		post.setCreateInfo();
		Long[] categorys = post.getCategorys();
		List<Category> categoryList = categoryService.findAll(Arrays.asList(categorys));
		List<Long> categoryids = new ArrayList<Long>();
		for(int i=0;i<categoryList.size();i++) {
			categoryids.add(categoryList.get(i).getId());
		}
		post.setCategorys(categoryids.toArray(new Long[]{}));
		Category defaultCategory = categoryService.getDefault(categoryList);
		if(defaultCategory!=null) {
			post.setCategory(defaultCategory.getId());
		}
		validate(post.validate());
		post = postService.addPost(post);
		resultBean.setData(post);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.post.edit")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Post post) {
		ResultBean resultBean = new ResultBean();
		if(post.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Post old = postService.findById(post.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setTitle(post.getTitle());
		old.setSummary(post.getSummary());
		old.setContent(post.getContent());
		old.setFixedLink(post.getFixedLink());
		old.setPic(post.getPic());
		old.setSourceFrom(post.getSourceFrom());
		old.setCommentEnable(post.getCommentEnable());
		old.setCommentShow(post.getCommentShow());
		old.setBadge(post.getBadge());
		old.setIsTop(post.getIsTop());
		old.setTopLevel(post.getTopLevel());
		old.setViewCount(post.getViewCount());
		old.setCommentCount(post.getCommentCount());
		old.setCollectionCount(post.getCollectionCount());
		old.setStatus(post.getStatus());
		old.setCategory(post.getCategory());
		old.setPublishDate(post.getPublishDate());
		old.setTagclouds(post.getTagclouds());
		old.setCategorys(post.getCategorys());
		Long[] categorys = post.getCategorys();
		List<Category> categoryList = categoryService.findAll(Arrays.asList(categorys));
		List<Long> categoryids = new ArrayList<Long>();
		for(int i=0;i<categoryList.size();i++) {
			categoryids.add(categoryList.get(i).getId());
		}
		old.setCategorys(categoryids.toArray(new Long[]{}));
		Category defaultCategory = categoryService.getDefault(categoryList);
		if(defaultCategory!=null) {
			old.setCategory(defaultCategory.getId());
		}
		old.setUpdateInfo();
		validate(old.validate());
		post = postService.updatePost(old);
		resultBean.setData(post);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.post.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Post post = postService.findById(id);
		if(post!=null) {
			post.setLogicDelete();
			postService.save(post);
			resultBean.setData(post);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
