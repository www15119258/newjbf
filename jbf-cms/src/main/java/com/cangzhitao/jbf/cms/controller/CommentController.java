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

import com.cangzhitao.jbf.cms.domain.Comment;
import com.cangzhitao.jbf.cms.domain.Post;
import com.cangzhitao.jbf.cms.domain.Spage;
import com.cangzhitao.jbf.cms.enmus.CommentStatus;
import com.cangzhitao.jbf.cms.enmus.CommentType;
import com.cangzhitao.jbf.cms.service.ICommentService;
import com.cangzhitao.jbf.cms.service.IPostService;
import com.cangzhitao.jbf.cms.service.ISpageService;
import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.IPUtil;
import com.cangzhitao.jbf.core.util.PageRequestUtil;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.security.entities.SysUser;
import com.cangzhitao.jbf.security.util.UserManager;

@Controller 
@RequestMapping("/${sys.path}/cms/comment")
public class CommentController extends BaseController {

	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IPostService postService;
	
	@Autowired
	private ISpageService spageService;
	
	@RequiresAuthentication
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public ResultBean query(Comment comment) {
		ResultBean resultBean = new ResultBean();
		Pageable pageable = PageRequestUtil.getPage(getRequest());
		Page<Comment> page = commentService.findAll(comment, pageable);
		if(page!=null&&page.getContent()!=null&&page.getContent().size()>0) {
			for(int i=0;i<page.getContent().size();i++) {
				Comment c = page.getContent().get(i);
				Long parent = c.getParent();
				if(parent!=null) {
					Comment parentComment = commentService.findById(parent);
					if(parentComment!=null) {
						c.setParentComment(parentComment);
					} else {
						c.setParent(null);
					}
				}
				CommentType type = c.getType();
				if(c.getBelong()!=null) {
					if(type==CommentType.PAGE) {
						Spage spage = spageService.findById(c.getBelong());
						if(spage!=null) {
							c.setBelongContent(spage);
						}
					} else if(type==CommentType.POST) {
						Post post = postService.findById(c.getBelong());
						if(post!=null) {
							c.setBelongContent(post);
						}
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
		Comment comment = commentService.findById(id);
		if(comment!=null) {
			resultBean.setData(comment);
			return resultBean;
		}
		resultBean.setStatus(ResultBean.ERROR);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.comment.add")
	@RequestMapping(value={"save"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean save(Comment comment) {
		ResultBean resultBean = new ResultBean();
		comment.setCreateInfo();
		validate(comment.validate());
		comment = commentService.save(comment);
		resultBean.setData(comment);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.comment.reply")
	@RequestMapping(value={"reply"}, method={RequestMethod.POST})
	@ResponseBody
	public ResultBean reply(Comment comment) {
		ResultBean resultBean = new ResultBean();
		comment.setNickname(null);
		comment.setStatus(CommentStatus.PASSED);
		SysUser sysUser = UserManager.getCurrentUser();
		if(sysUser!=null) {
			comment.setNickname(sysUser.getNickname());
			comment.setEmail(sysUser.getEmail());
			comment.setIp(IPUtil.getRequestIp(getRequest()));
		}
		comment.setCreateInfo();
		validate(comment.validate());
		comment = commentService.save(comment);
		resultBean.setData(comment);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.comment.reply")
	@RequestMapping(value={"update"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean update(Comment comment) {
		ResultBean resultBean = new ResultBean();
		if(comment.getId()==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		Comment old = commentService.findById(comment.getId());
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		old.setStatus(comment.getStatus());
		validate(old.validate());
		comment = commentService.save(old);
		resultBean.setData(comment);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.comment.verify")
	@RequestMapping(value={"updateStatus"}, method={RequestMethod.PUT})
	@ResponseBody
	public ResultBean updateStatus(Long id, String status) {
		ResultBean resultBean = new ResultBean();
		Comment old = commentService.findById(id);
		if(old==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
			return resultBean;
		}
		CommentStatus commentStatus = CommentStatus.getEnum(status);
		if(commentStatus==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("非法的状态！");
			return resultBean;
		}
		old.setStatus(commentStatus);
		old = commentService.save(old);
		resultBean.setData(old);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.cms.comment.delete")
	@RequestMapping(value={"delete/{id}"}, method={RequestMethod.DELETE})
	@ResponseBody
	public ResultBean delete(@PathVariable Long id) {
		ResultBean resultBean = new ResultBean();
		Comment comment = commentService.findById(id);
		if(comment!=null) {
			List<Comment> comments = commentService.findTreeAllChildren(id);
			comments.add(0, comment);
			for(int i=0;i<comments.size();i++) {
				comments.get(i).setLogicDelete();
			}
			commentService.save(comments);
			resultBean.setData(comments);
		} else {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("数据不存在！");
		}
		return resultBean;
	}
	
}
