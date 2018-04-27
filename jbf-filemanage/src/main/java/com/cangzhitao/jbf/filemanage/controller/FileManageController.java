package com.cangzhitao.jbf.filemanage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.ResultBean;
import com.cangzhitao.jbf.filemanage.enums.FileType;
import com.cangzhitao.jbf.filemanage.util.FileManageUtil;
import com.cangzhitao.jbf.filemanage.vo.FileQuery;
import com.cangzhitao.jbf.filemanage.vo.FileVO;
import com.cangzhitao.jbf.filemanage.vo.NetFileVO;

@Controller
@RequestMapping(value = "/${sys.path}/filemanage")
public class FileManageController extends BaseController {
	
	@RequiresPermissions(value="jbf.filemanage.file.view")
	@RequestMapping(value={"query"}, method={RequestMethod.GET})
	@ResponseBody
	public Object query(FileQuery fileQuery) {
		List<FileVO> files = null;
		String types = fileQuery.getTypes();
		if(types==null||"".equals(types)) {
			files = FileManageUtil.listFile(fileQuery.getPath());
		} else {
			List<FileType> fileTypeList = new ArrayList<FileType>();
			String[] ts = types.split(",");
			for(int i=0;i<ts.length;i++) {
				FileType fileType = FileType.getEnum(ts[i]);
				if(fileType!=null) {
					fileTypeList.add(fileType);
				}
			}
			files = FileManageUtil.listFile(fileQuery.getPath(), fileTypeList.toArray(new FileType[]{}));
		}
		return files;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.newFolder")
	@RequestMapping(value={"newFolder"}, method={RequestMethod.POST})
	@ResponseBody
	public Object newFolder(String path, String name) {
		ResultBean resultBean = new ResultBean();
		if(path==null||"".equals(path)) {
			path = "/";
		}
		if(path.startsWith("/")==false) {
			path = "/" + path;
		}
		if(name==null||"".equals(name)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件夹名称不能为空！");
			return resultBean;
		}
		FileVO fileVO = FileManageUtil.getFile(path, name);
		if(fileVO!=null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件夹已经存在！");
			return resultBean;
		}
		FileManageUtil.newFolder(path, name);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.rename")
	@RequestMapping(value={"renameFile"}, method={RequestMethod.PUT})
	@ResponseBody
	public Object renameFile(String path, String oldName, String newName) {
		ResultBean resultBean = new ResultBean();
		if(path==null||"".equals(path)) {
			path = "/";
		}
		if(path.startsWith("/")==false) {
			path = "/" + path;
		}
		if(oldName==null||"".equals(oldName)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件不存在！");
			return resultBean;
		}
		if(newName==null||"".equals(newName)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件名称不能为空！");
			return resultBean;
		}
		FileVO fileVO = FileManageUtil.getFile(path, oldName);
		if(fileVO==null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("原文件不存在！");
			return resultBean;
		}
		fileVO = FileManageUtil.getFile(path, newName);
		if(fileVO!=null) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件名已存在！");
			return resultBean;
		}
		FileManageUtil.renameFile(path, oldName, newName);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.delete")
	@RequestMapping(value={"deleteFile"}, method={RequestMethod.DELETE})
	@ResponseBody
	public Object deleteFile(String path, String files) {
		ResultBean resultBean = new ResultBean();
		if(path==null||"".equals(path)) {
			path = "/";
		}
		if(path.startsWith("/")==false) {
			path = "/" + path;
		}
		if(files==null||"".equals(files)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件不存在！");
			return resultBean;
		}
		String[] fs = files.split("\\|\\|@\\|\\|");
		for(int i=0;i<fs.length;i++) {
			FileManageUtil.deleteFile(path, fs[i]);
		}
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.copy")
	@RequestMapping(value={"copyFile"}, method={RequestMethod.POST})
	@ResponseBody
	public Object copyFile(String oldPath, String newPath, String files, Integer type) {
		ResultBean resultBean = new ResultBean();
		if(oldPath==null||"".equals(oldPath)) {
			oldPath = "/";
		}
		if(oldPath.startsWith("/")==false) {
			oldPath = "/" + oldPath;
		}
		if(newPath==null||"".equals(newPath)) {
			newPath = "/";
		}
		if(newPath.startsWith("/")==false) {
			newPath = "/" + newPath;
		}
		if(files==null||"".equals(files)) {
			resultBean.setStatus(ResultBean.ERROR);
			resultBean.setMessage("文件不存在！");
			return resultBean;
		}
		String[] fs = files.split("\\|\\|@\\|\\|");
		for(int i=0;i<fs.length;i++) {
			if(type==0) {
				FileManageUtil.copyFile(oldPath, fs[i], newPath, fs[i]);
			} else {
				FileManageUtil.renameFile(oldPath, fs[i], newPath, fs[i]);
			}
		}
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.uploadFromNet")
	@RequestMapping(value={"uploadFromNet"}, method={RequestMethod.POST})
	@ResponseBody
	public Object uploadFromNet(@RequestBody List<NetFileVO> netFiles) {
		List<FileVO> fileList = FileManageUtil.uploadFromNet(netFiles);
		ResultBean resultBean = new ResultBean();
		resultBean.setData(fileList);
		return resultBean;
	}
	
	@RequiresPermissions(value="jbf.filemanage.file.uploadFromLocal")
	@RequestMapping(value={"uploadFromLocal"}, method={RequestMethod.POST})
	@ResponseBody
	public Object uploadFromLocal(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        FileVO fileVO = FileManageUtil.uploadFromLocal(file, request.getParameter("path"));
        ResultBean resultBean = new ResultBean();
		resultBean.setData(fileVO);
		return resultBean;
	}

}
