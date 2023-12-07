package com.unitech.UniTech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//UserRegistrationDTO.java

public class UserRegistrationDTO {


	private String username;
	private String password;
	private String cpassword;
	private String pincode;
 public UserRegistrationDTO() {
		super();
	}
public UserRegistrationDTO(String username, String password, String cpassword, String pincode) {
		super();
		this.username = username;
		this.password = password;
		this.cpassword = cpassword;
		this.pincode = pincode;
	}
// other registration fields, if needed
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
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

 // getters and setters
}
