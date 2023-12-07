package com.unitech.UniTech.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.unitech.UniTech.repository.AccountRepo;
import com.unitech.UniTech.repository.UserRepo;

@Service
public class ValidationService {

	private UserRepo userRepo;
	private AccountRepo accountRepo;

	
	@Autowired
	public ValidationService(UserRepo userRepo,
			AccountRepo accountRepo
			)
	{
		this.userRepo = userRepo;
		this.accountRepo= accountRepo;
	}
	
	public BindingResult pinCodeValidation(
			String pincode,
			BindingResult bindingResult
			)
	{
		 if(pincode.length()!=4 ) {
	            bindingResult.rejectValue("pincode", "1", "Must be 4 digits only");
	        }
		 if(!pincode.matches("\\d+")) 
		 {
			 bindingResult.rejectValue("pincode", "1", "Must be only digits");
		 }
		 
		 if((userRepo.findByPincode(pincode).isPresent())) {
	            bindingResult.rejectValue("pincode", "1", "Pincode already registered!");
	        }
		
		
		return bindingResult;	
	}
	
	
	public BindingResult usernameValidation(
			String username,
			BindingResult bindingResult
			)
	{
		 if(username.length()<4 ||  username.length()>30) {
	            bindingResult.rejectValue("username", "1", "Must be between 4 and 30 symbols only");
	        }
		
		 
		 if((userRepo.findByUsername(username).isPresent())) {
	            bindingResult.rejectValue("username", "2", "Username already registered!");
	        }

		return bindingResult;	
	}
	
	
	public BindingResult passwordValidation(
			String password,
			String cpassword,
			BindingResult bindingResult
			)
	{
		 if(password.length()<4 ||  password.length()>30) {
	            bindingResult.rejectValue("password", "3", "Must be between 4 and 30 symbols only");
	        }
		if(!password.equals(cpassword))
		{
			bindingResult.rejectValue("cpassword", "3", "Password are not equal");
		}


		return bindingResult;	
	}
	
	
	
	public BindingResult targetAccountNumberValidation(
			String targetAccountNumber,
			BindingResult bindingResult
			)
	{
		 if(!(accountRepo.findByName(targetAccountNumber).isPresent())) {
	            bindingResult.rejectValue("targetAccountId", "1", "Account doesent exist");
	        }

		return bindingResult;	
	}
	
	public BindingResult enougMoneyValidation(
			Float value,
			String sourceAccountNumber,
			BindingResult bindingResult
			)
	{
		Float cuurentMoney = accountRepo.findByName(sourceAccountNumber).get().getMoney();
		
		
		
		 if (cuurentMoney < value) {
	            bindingResult.rejectValue("amount", "1", "Not enough money");
	        }

		return bindingResult;	
	}
	
	
	
	
	
	
	public BindingResult accountIsActiveValidation(
			String targetAccountNumber,
			BindingResult bindingResult
			)
	{
		
		Boolean accountStatus = accountRepo.findByName(targetAccountNumber).get().isActivityStatus();
		 if(accountStatus==false) {
	            bindingResult.rejectValue("targetAccountId", "1", "Account is not active!");
	        }

		return bindingResult;	
	}
	
	
}
