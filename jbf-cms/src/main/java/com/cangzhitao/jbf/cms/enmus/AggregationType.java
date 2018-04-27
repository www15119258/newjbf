package com.cangzhitao.jbf.cms.enmus;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum AggregationType implements BaseEnum {
	
	/**
	 * 文章
	 */
	POST("0"), 
	/**
	 * 页面
	 */
	SPAGE("1"),
	/**
	 * 分类目录
	 */
	CATEGORY("2"),
	/**
	 * 自定义链接
	 */
	LINK("3")
	;

	private String code;

    private AggregationType(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static AggregationType getEnum(String code) {
        for(AggregationType s:AggregationType.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
