package com.cangzhitao.jbf.security.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.cangzhitao.jbf.core.entities.BaseEntity;
import com.cangzhitao.jbf.security.enums.Sex;
import com.cangzhitao.jbf.security.enums.SysUserStatus;
import com.cangzhitao.jbf.validation.annotation.Email;
import com.cangzhitao.jbf.validation.annotation.Mobile;
import com.cangzhitao.jbf.validation.annotation.NotEmpty;
import com.cangzhitao.jbf.validation.annotation.NotNull;
import com.cangzhitao.jbf.validation.annotation.Size;

@Entity
@Table(name="t_sys_user")
public class SysUser extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8032821075196246995L;

	@NotEmpty(message="用户名不能为空")
	@Size(min=2, max=20, message="用户名的长度必须在{min}和{max}之间")
	private String username;
	
	@JSONField(serialize=false)
	@NotEmpty(message="密码不能为空")
	private String password;
	
	@NotEmpty(message="昵称不能为空")
	@Size(min=2, max=20, message="昵称的长度必须在{min}和{max}之间")
	private String nickname;
	
	private String icon;
	
	@Email(message="非法的Email地址")
	private String email;
	
	@Mobile(empty=true, message="非法的手机号码")
	private String mobile;
	
	private Sex sex;
	
	private Integer age;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date birthday;

	@NotNull(message="用户状态不能为空")
	private SysUserStatus status = SysUserStatus.NORMAL;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public SysUserStatus getStatus() {
		return status;
	}

	public void setStatus(SysUserStatus status) {
		this.status = status;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
}
