package com.fourhorizons.web.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userInfo_seq")
	@SequenceGenerator(name="userInfo_seq", sequenceName="userInfo_seq", initialValue=1, allocationSize=1)
	private Integer id;

	private String facebookId;
	
	private String nick;

	private String name;

	private String email;

	private String gender;

	public Integer getId() {
		return id;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
