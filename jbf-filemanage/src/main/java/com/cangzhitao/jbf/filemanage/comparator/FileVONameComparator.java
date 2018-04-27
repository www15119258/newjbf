package com.cangzhitao.jbf.filemanage.comparator;

import java.util.Comparator;

import com.cangzhitao.jbf.filemanage.enums.FileType;
import com.cangzhitao.jbf.filemanage.vo.FileVO;

/**
 * Created by lihui on 16/6/3.
 */
public class FileVONameComparator implements Comparator<FileVO> {


    public int compare(FileVO o1, FileVO o2) {
        int f1 = o1.getType()==FileType.FOLDER?1:0;
        int f2 = o2.getType()==FileType.FOLDER?1:0;
        if(f1!=f2) {
            return f2 - f1;
        }
        int n = o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        if(n!=0) {
            return n;
        }
        return o1.getName().compareTo(o2.getName());
    }
}
