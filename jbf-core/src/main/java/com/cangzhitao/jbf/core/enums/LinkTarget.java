package com.cangzhitao.jbf.core.enums;

public enum LinkTarget implements BaseEnum {

	_blank("_blank"), 
	_self("_self"),
	_parent("_parent"),
	_top("_top")
	;

	private String code;

    private LinkTarget(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static LinkTarget getEnum(String code) {
        for(LinkTarget s:LinkTarget.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
    
}
