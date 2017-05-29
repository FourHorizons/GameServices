package com.fourhorizons.web.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class UserPoints {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userPoints_seq")
	@SequenceGenerator(name = "userPoints_seq", sequenceName = "userPoints_seq", initialValue = 1, allocationSize = 1)
	private Integer id;

	private String nick;

	private Integer Points;

	public Integer getId() {
		return id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getPoints() {
		return Points;
	}

	public void setPoints(Integer Points) {
		this.Points = Points;
	}
}
