package com.cangzhitao.jbf.core.enums;

public enum Enable implements BaseEnum {
	
	/**
	 * 禁用
	 */
	FALSE("0"), 
	/**
	 * 启用
	 */
	TRUE("1")
	;

	private String code;

    private Enable(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static Enable getEnum(String code) {
        for(Enable s:Enable.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
