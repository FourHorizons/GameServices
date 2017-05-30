package com.fourhorizons.web.service.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class UserPositions {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userPositions_seq")
	@SequenceGenerator(name="userPositions_seq", sequenceName="userPositions_seq", initialValue=1, allocationSize=1)
	private Integer id;

	private String nick;

	@Column(scale = 7, precision = 19)
	private BigDecimal latitude;

	@Column(scale = 7, precision = 19)
	private BigDecimal longitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}
