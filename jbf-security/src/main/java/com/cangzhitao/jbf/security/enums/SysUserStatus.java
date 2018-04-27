package com.cangzhitao.jbf.security.enums;

import com.cangzhitao.jbf.core.enums.BaseEnum;

public enum SysUserStatus implements BaseEnum {
	
	/**
	 * 正常
	 */
	NORMAL("0"), 
	/**
	 * 锁定
	 */
	LOCK("1"),
	/**
	 * 未激活
	 */
	NOT_ACTIVE("2")
	;

	private String code;

    private SysUserStatus(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static SysUserStatus getEnum(String code) {
        for(SysUserStatus s:SysUserStatus.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }
	
}
