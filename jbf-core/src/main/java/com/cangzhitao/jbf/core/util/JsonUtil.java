package com.cangzhitao.jbf.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, SerializerFeature.BrowserSecure, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteDateUseDateFormat);
	}
	
}
