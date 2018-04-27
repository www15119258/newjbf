package com.cangzhitao.jbf.cms.enmus;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum CommentType implements BaseEnum {
	
	/**
	 * 文章
	 */
	POST("0"), 
	/**
	 * 页面
	 */
	PAGE("1")
	;

	private String code;

    private CommentType(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static CommentType getEnum(String code) {
        for(CommentType s:CommentType.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
