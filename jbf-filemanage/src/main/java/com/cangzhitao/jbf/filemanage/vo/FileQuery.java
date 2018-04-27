package com.cangzhitao.jbf.filemanage.vo;

import java.io.Serializable;

/**
 * Created by lihui on 16/6/1.
 */
public class FileQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5842918367821284741L;

    private String types;

    private String path;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
}
