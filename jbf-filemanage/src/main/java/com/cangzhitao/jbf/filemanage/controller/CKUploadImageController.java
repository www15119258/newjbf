package com.cangzhitao.jbf.filemanage.controller;

import java.io.File;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cangzhitao.jbf.core.controller.BaseController;
import com.cangzhitao.jbf.core.util.MD5Util;
import com.cangzhitao.jbf.core.util.PropertiesUtil;

@Controller
@RequestMapping(value = "/${upload.requestpath}")
public class CKUploadImageController extends BaseController {

	private String rootPath = PropertiesUtil.getProperty("filemanage", "upload.savepath");

	private String downloadPath = PropertiesUtil.getProperty("filemanage", "upload.downloadpath");

	@RequestMapping(value = "/upload/uploadImgForCK.do")
	@ResponseBody
	public void uploadFile(@RequestParam(value = "upload", required = true) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		Calendar c = Calendar.getInstance();
		String imagePath = System.getProperty("file.separator") + c.get(Calendar.YEAR)
				+ System.getProperty("file.separator") + (c.get(Calendar.MONTH) + 1)
				+ System.getProperty("file.separator") + c.get(Calendar.DAY_OF_MONTH);
		String path = "";
		path = rootPath + imagePath;
		String fileName = file.getOriginalFilename();
		fileName = MD5Util.encodeMD5(c.getTimeInMillis() + fileName, 6)
				+ fileName.substring(fileName.lastIndexOf(".") == -1 ? 0 : fileName.lastIndexOf("."));
		File targetFile = new File(path, fileName);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		while (targetFile.exists()) {
			fileName = MD5Util.encodeMD5(c.getTimeInMillis() + fileName, 6)
					+ fileName.substring(fileName.lastIndexOf(".") == -1 ? 0 : fileName.lastIndexOf("."));
			targetFile = new File(path, fileName);
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String imageURL = downloadPath + imagePath + System.getProperty("file.separator") + targetFile.getName();
		String callback = request.getParameter("CKEditorFuncNum");
		String msg = "<script type=\"text/javascript\">" + "window.parent.CKEDITOR.tools.callFunction(" + callback
				+ ",'" + imageURL + "',''" + ")" + "</script>";
		putInToHtmlResponse(msg);
	}

}
