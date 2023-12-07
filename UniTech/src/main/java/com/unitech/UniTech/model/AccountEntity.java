package com.unitech.UniTech.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class AccountEntity {
	
	 @jakarta.persistence.Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Long userId;
	private String name;
	private boolean ActivityStatus;
	private Float Money;
	
	
	public AccountEntity(Long id, Long userId, String name, boolean activityStatus, Float money) {
		super();
		Id = id;
		this.userId = userId;
		this.name = name;
		ActivityStatus = activityStatus;
		Money = money;
	}
	
	
	public AccountEntity() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActivityStatus() {
		return ActivityStatus;
	}
	public void setActivityStatus(boolean activityStatus) {
		ActivityStatus = activityStatus;
	}
	public Float getMoney() {
		return Money;
	}
	public void setMoney(Float money) {
		Money = money;
	}

}
