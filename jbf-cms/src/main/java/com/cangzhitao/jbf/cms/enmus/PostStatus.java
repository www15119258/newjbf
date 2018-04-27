package com.cangzhitao.jbf.cms.enmus;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum PostStatus implements BaseEnum {
	
	/**
	 * 暂存
	 */
	SAVE("0"), 
	/**
	 * 发布
	 */
	PUBLISH("1")
	;

	private String code;

    private PostStatus(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static PostStatus getEnum(String code) {
        for(PostStatus s:PostStatus.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
