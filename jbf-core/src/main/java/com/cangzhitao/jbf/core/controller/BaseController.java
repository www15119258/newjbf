package com.cangzhitao.jbf.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cangzhitao.jbf.core.annotation.ValidateException;
import com.cangzhitao.jbf.core.util.RequestResponseContextUtil;
import com.cangzhitao.jbf.core.util.StringUtil;

public class BaseController {
	
	public HttpServletRequest getRequest() {
        return RequestResponseContextUtil.getRequest();
    }


    public HttpServletResponse getResponse() {
        return RequestResponseContextUtil.getResponse();
    }
    
    public void validate(List<Map<String, String>> mapList) {
    	if(mapList==null||mapList.size()==0) {
    		return;
    	}
    	List<String> messageList = new ArrayList<String>();
    	for(int i=0;i<mapList.size();i++) {
    		messageList.addAll(mapList.get(i).values());
    	}
    	String[] message = messageList.toArray(new String[]{});
    	throw new ValidateException(StringUtil.join(message, ";"));
    }

    public void putInToHtmlResponse(String content) {
        try {
            this.getResponse().setContentType("text/html;charset=UTF-8");
            this.getResponse().setCharacterEncoding("UTF-8");
            this.getResponse().getWriter().print(content);
            this.getResponse().getWriter().flush();
            this.getResponse().getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
