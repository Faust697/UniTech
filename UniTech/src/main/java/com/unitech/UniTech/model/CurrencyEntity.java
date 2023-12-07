package com.unitech.UniTech.model;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Based on USD in this case
@Entity
public class CurrencyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Float currencyValue; 
	private String lastModificationDate; // Change to normal Date format
	
	public CurrencyEntity() {
	}
	
	public CurrencyEntity(Long id, String name, Float currencyValue, String lastModificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.currencyValue = currencyValue;
		this.lastModificationDate = lastModificationDate;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getCurrencyValue() {
		return currencyValue;
	}
	
	public void setCurrencyValue(Float currencyValue) {
		this.currencyValue = currencyValue;
	}
	
	public String getLastModificationDate() {
		return lastModificationDate;
	}
	
	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
}
