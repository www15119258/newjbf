package com.cangzhitao.jbf.cms.enmus;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum SourceFrom implements BaseEnum {
	
	/**
	 * 原创
	 */
	ORIGINAL("0"), 
	/**
	 * 转载
	 */
	COPY("1")
	;

	private String code;

    private SourceFrom(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static SourceFrom getEnum(String code) {
        for(SourceFrom s:SourceFrom.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
