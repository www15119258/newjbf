package com.cangzhitao.jbf.filemanage.enums;

import com.cangzhitao.jbf.core.enums.BaseEnum;

/**
 * Created by lihui on 16/5/13.
 */
public enum FileType implements BaseEnum {

    FOLDER("0"), FILE("1"), ZIP("2"), RAR("3"), IMAGE("4"), SOUND("5"), MOVIE("6");

    private String code;

    private FileType(String code) {
        this.code = code;
    }

    public String toString() {
        return String.valueOf(code);
    }

    public String value() {
        return code;
    }

    public static FileType getEnum(String code) {
        for(FileType s:FileType.class.getEnumConstants()) {
            if(s.code.equals(code)) {
                return s;
            }
        }
        return null;
    }

}
