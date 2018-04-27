package com.cangzhitao.jbf.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PageRequestUtil {
	
	public static final Integer MAX_PAGE_SIZE = 100;
	
	public static Pageable getPage(HttpServletRequest request) {
		String sorder = request.getParameter("_query_order");
		if(sorder==null||"".equals(sorder)) {
			return getPage(request, null);
		}
		String[] ssorder = sorder.split(",");
		List<Order> orderList = new ArrayList<Order>();
		for(int i=0;i<ssorder.length;i++) {
			String s = ssorder[i];
			s = s.trim();
			String[] strs = s.split("\\s");
			Direction direction = Direction.ASC;
			if(strs.length>1) {
				direction = Direction.fromString(strs[1].trim());
			}
			Order o = new Order(direction, strs[0].trim());
			orderList.add(o);
		}
		Sort sort = Sort.by(orderList);
		return getPage(request, sort);
	}
	
	public static Pageable getPage(HttpServletRequest request, Sort sort) {
		int page = Integer.parseInt(StringUtil.getString(request.getParameter("_query_page"), "0"));
		int size = Integer.parseInt(StringUtil.getString(request.getParameter("_query_size"), "10"));
		String pagesize = request.getParameter("_query_limit");
		String offset = request.getParameter("_query_offset");
		if(pagesize!=null&&offset!=null) {
			int pageSize = 10;
	        if(pagesize==null||"".equals(pagesize)) {
	            pageSize = 10;
	        } else {
	            pageSize = Integer.parseInt(pagesize);
	        }

	        int pageNum = 0;
	        pageNum = (Integer.parseInt(offset)+pageSize)/pageSize;
	        pageNum = pageNum-1;
	        if(pageNum<0) {
	            pageNum = 0;
	        }
	        page = pageNum;
	        size = pageSize;
		}
        page = page - 1;
		if(page<0) {
			page = 0;
		}
		if(size<=0) {
			size = 10;
		}
		if(size>MAX_PAGE_SIZE) {
			size = MAX_PAGE_SIZE;
		}
		if(sort==null) {
			return PageRequest.of(page, size);
		} else {
			return PageRequest.of(page, size, sort);
		}
	}

}
