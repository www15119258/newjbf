package com.cangzhitao.jbf.cms.enmus;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum CommentStatus implements BaseEnum {
	
	/**
	 * 暂存
	 */
	SAVE("0"), 
	/**
	 * 审核通过
	 */
	PASSED("1"),
	/**
	 * 审核不通过
	 */
	REJECT("2")
	;

	private String code;

    private CommentStatus(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static CommentStatus getEnum(String code) {
        for(CommentStatus s:CommentStatus.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
