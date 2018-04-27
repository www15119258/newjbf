package com.cangzhitao.jbf.security.enums;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum Sex implements BaseEnum {
	
	/**
	 * 男性
	 */
	MAEL("0"), 
	/**
	 * 女性
	 */
	FEMAEL("1")
	;

	private String code;

    private Sex(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static Sex getEnum(String code) {
        for(Sex s:Sex.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
