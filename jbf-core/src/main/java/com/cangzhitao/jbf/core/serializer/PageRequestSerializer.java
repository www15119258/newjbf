package com.cangzhitao.jbf.core.serializer;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;

import org.springframework.data.domain.PageRequest;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.cangzhitao.jbf.core.util.JsonUtil;

public class PageRequestSerializer implements ObjectSerializer {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();  
        if (object ==null) {  
            return ;  
        }  
        PageRequest pageRequest = (PageRequest)object;
        Page page = new Page(pageRequest.getPageNumber()+"", pageRequest.getPageSize()+"", pageRequest.getSort()==null?"":pageRequest.getSort().toString());
        out.write(JsonUtil.toJSONString(page));  
	}
	
	static class Page implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7790191590268639477L;
		String number;
		String size;
		String sort;
		public Page(String number, String size, String sort) {
			this.number = number;
			this.size = size;
			this.sort = sort;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getSort() {
			return sort;
		}
		public void setSort(String sort) {
			this.sort = sort;
		}
		
	}

}
