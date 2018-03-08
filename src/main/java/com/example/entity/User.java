package com.example.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = -4565423276613762049L;
	
	private String id;
	private String name;
	private String sex;
	private Date birth;
	private Boolean chinese;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Boolean isChinese() {
		return chinese;
	}
	public void setChinese(Boolean chinese) {
		this.chinese = chinese;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + ", birth=" + birth + ", chinese=" + chinese + "]";
	}
}
