package com.cangzhitao.jbf.statistics.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_statistics_visitlog")
public class VisitLog extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4009729340588444506L;

	/**
	 * 访问链接
	 */	
	@Lob
	@NotEmpty(message="访问链接不能为空")
	@Size(min=1, max=2000, message="访问链接的长度不能超过{max}")
	private String url;
	
	/**
	 * 访问页面标题
	 */	
	@Lob
	@Size(min=0, max=2000, message="页面标题的长度不能超过{max}")
	private String pagetitle;
	
	/**
	 * 访问来源地址
	 */	
	@Lob
	@Size(min=1, max=2000, message="访问来源地址的长度不能超过{max}")
	private String reffer;
	
	/**
	 * 访问者IP
	 */	
	@Size(min=0, max=20, message="IP地址长度非法")
	private String ip;
	
	/**
	 * 浏览器版本
	 */	
	@Size(min=0, max=200, message="浏览器版本的长度不能超过{max}")
	private String browser;
	
	/**
	 * 访问描述
	 */	
	@Lob				
	@Size(min=0, max=2000, message="访问描述的长度不能超过{max}")
	private String description;
	
	private String country;
	
	private String area;
	
	private String province;
	
	private String city;
	
	private String isp;
	
	private String visitor;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPagetitle() {
		return pagetitle;
	}

	public void setPagetitle(String pagetitle) {
		this.pagetitle = pagetitle;
	}

	public String getReffer() {
		return reffer;
	}

	public void setReffer(String reffer) {
		this.reffer = reffer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}
	
}
