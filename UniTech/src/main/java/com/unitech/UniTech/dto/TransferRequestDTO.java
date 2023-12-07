package com.unitech.UniTech.dto;

import java.math.BigDecimal;

//TransferRequestDTO.java

public class TransferRequestDTO {

 private String sourceAccountId;
 private String targetAccountId;
 private String userPincode;
 private Float amount;
 
 
public String getSourceAccountId() {
	return sourceAccountId;
}
public void setSourceAccountId(String sourceAccountId) {
	this.sourceAccountId = sourceAccountId;
}
public String getTargetAccountId() {
	return targetAccountId;
}
public void setTargetAccountId(String targetAccountId) {
	this.targetAccountId = targetAccountId;
}
public String getUserPincode() {
	return userPincode;
}
public void setUserPincode(String userPincode) {
	this.userPincode = userPincode;
}
public Float getAmount() {
	return amount;
}
public void setAmount(Float amount) {
	this.amount = amount;
}

 // getters and setters
}

