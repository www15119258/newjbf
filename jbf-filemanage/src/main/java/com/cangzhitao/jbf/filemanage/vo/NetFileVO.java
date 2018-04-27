package com.cangzhitao.jbf.filemanage.vo;

import java.io.Serializable;

public class NetFileVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2562484555084796545L;

	private String path;
	
	private String url;
	
	private String name;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
