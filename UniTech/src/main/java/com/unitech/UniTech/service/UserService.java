package com.unitech.UniTech.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.unitech.UniTech.dto.AccountDTO;
import com.unitech.UniTech.dto.TransferRequestDTO;
import com.unitech.UniTech.dto.UserLoginDTO;
import com.unitech.UniTech.dto.UserRegistrationDTO;

//UserService.java

@Service
@Component
public interface UserService {

 void registerUser(UserRegistrationDTO registrationDTO);

 void loginUser(UserLoginDTO loginDTO);

 List<AccountDTO> getAccounts(Long userId);

 void makeTransfer(TransferRequestDTO transferRequestDTO);

 BigDecimal getCurrencyRate(String currencyPair);
}

