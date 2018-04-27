package com.cangzhitao.jbf.core.enums;

public enum Visible implements BaseEnum {
	
	/**
	 * 不可见
	 */
	FALSE("0"), 
	/**
	 * 可见
	 */
	TRUE("1")
	;

	private String code;

    private Visible(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static Visible getEnum(String code) {
        for(Visible s:Visible.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
