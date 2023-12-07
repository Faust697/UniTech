package com.unitech.UniTech.dto;

//UserLoginDTO.java

public class UserLoginDTO {

 private String pin;
 private String username;
 private String password;
 
 
public UserLoginDTO() {
	super();
}
public UserLoginDTO(String pin, String username, String password) {
	super();
	this.pin = pin;
	this.username = username;
	this.password = password;
}
public String getPin() {
	return pin;
}
public void setPin(String pin) {
	this.pin = pin;
}
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

 // getters and setters
}
