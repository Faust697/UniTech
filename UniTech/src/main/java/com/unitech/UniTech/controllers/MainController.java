package com.unitech.UniTech.controllers;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.unitech.UniTech.dto.TransferRequestDTO;
import com.unitech.UniTech.dto.UserLoginDTO;
import com.unitech.UniTech.dto.UserRegistrationDTO;
import com.unitech.UniTech.model.AccountEntity;
import com.unitech.UniTech.model.CurrencyEntity;
import com.unitech.UniTech.model.TransactionEntity;
import com.unitech.UniTech.model.UserEntity;
import com.unitech.UniTech.repository.AccountRepo;
import com.unitech.UniTech.repository.CurrencyRepo;
import com.unitech.UniTech.repository.TransactionRepo;
import com.unitech.UniTech.repository.UserRepo;
import com.unitech.UniTech.service.CurrencyConversionService;
import com.unitech.UniTech.service.ValidationService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




@Controller
@RequestMapping("/")
public class MainController {

	
	private UserRepo userRepo;
	private AccountRepo accountRepo;
	private TransactionRepo transactionRepo;
	private CurrencyRepo currencyRepo;
	private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ValidationService validationService;
    private final CurrencyConversionService conversionService;
    Random random = new Random();

	@Autowired
	 public MainController(
			 PasswordEncoder passwordEncoder,
			 AuthenticationManager authenticationManager,
			 UserRepo userRepo,
			 AccountRepo accountRepo,
			 TransactionRepo transactionRepo,
			 CurrencyRepo currencyRepo,
			 ValidationService validationService,
			 CurrencyConversionService conversionService) {
		
			super();
			this.authenticationManager = authenticationManager;
			this.userRepo = userRepo;
			this.accountRepo = accountRepo;
			this.transactionRepo = transactionRepo;
			this.currencyRepo = currencyRepo;
			this.passwordEncoder = passwordEncoder;
			this.validationService=validationService;
			this.conversionService = conversionService;
		}
	
	
	
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }
    
    
  @GetMapping("/login")
    public String showLoginForm(Model model) {
    	  model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }

    
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/"; // Перенаправление на главную страницу после выхода
    }

  
    @GetMapping("/create-account")
    public String createBankAccount(Model model) {
    	UserEntity user = getUser();
if(user!=null)
{
	AccountEntity account = new AccountEntity();
	
	StringBuilder stringBuilder = new StringBuilder();
	ThreadLocalRandom.current().ints(12, 0, 10)
    .forEach(digit -> stringBuilder.append(digit));
	
	account.setActivityStatus(true);
	account.setMoney(new Float(new DecimalFormat("#.##").format(1 + (1000 - 1) * random.nextDouble())));
	account.setName(stringBuilder.toString());
	account.setUserId(user.getId());
	accountRepo.save(account);
}
        return "redirect:/user-details"; // Перенаправление на главную страницу после выхода
    }

    
    
    

    
    
    
    @PostMapping("/transfer")
    public String transferMoney(@ModelAttribute("transferRequestDTO") TransferRequestDTO transferRequestDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        System.out.println("User Pincode: " + transferRequestDTO.getUserPincode());
        System.out.println("User source account: " + transferRequestDTO.getSourceAccountId());
        System.out.println("Recipient account : " + transferRequestDTO.getTargetAccountId());
        System.out.println("Amount: " + transferRequestDTO.getAmount());

        validationService.targetAccountNumberValidation(transferRequestDTO.getTargetAccountId(), bindingResult);
        validationService.enougMoneyValidation(transferRequestDTO.getAmount(), transferRequestDTO.getSourceAccountId(), bindingResult);
        validationService.accountIsActiveValidation(transferRequestDTO.getTargetAccountId(), bindingResult);
        // Проверки и обработка ошибок
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.transferRequestDTO", bindingResult);
            redirectAttributes.addFlashAttribute("transferRequestDTO", transferRequestDTO);
            return "redirect:/user-details";
        }
        
        AccountEntity source = accountRepo.findByName(transferRequestDTO.getSourceAccountId()).get();
        AccountEntity target = accountRepo.findByName(transferRequestDTO.getTargetAccountId()).get();
        
        source.setMoney(source.getMoney()-transferRequestDTO.getAmount());
        target.setMoney(target.getMoney()+transferRequestDTO.getAmount());
        
        
        accountRepo.save(source);
        accountRepo.save(target);
        
        
        System.out.println("Ostalos deneg "+ source.getMoney());
        System.out.println("Stalo deneg "+ target.getMoney());
        
        TransactionEntity transaction = new TransactionEntity(
        		transferRequestDTO.getSourceAccountId(), 
        		transferRequestDTO.getTargetAccountId(), 
        		transferRequestDTO.getAmount(), 
        		"AZN", 
        		LocalDateTime.now(),
        		true);
        transactionRepo.save(transaction);
        
        return "redirect:/user-details";
    }

    
    
    @GetMapping("/changeStatus")
    public String deactivateAccount(@RequestParam("userId") String userId, @RequestParam("accountName") String accountName, Model model) {
    	
    	UserEntity currentUser = getUser();
    	String a1 = currentUser.getId().toString();
    	String a2 = userId;

;
    	if(a1.equals(a2))
    	{
    	  	System.out.println(a1+"equal"+a2+"!");
    	        // Логика деактивации аккаунта, например, изменение статуса в базе данных
    	        AccountEntity account = accountRepo.findByName(accountName).get();
    	        		
    	        account.setActivityStatus(!account.isActivityStatus());
    	        System.out.print("Now status is "+account.isActivityStatus());
    	        accountRepo.save(account);
    	        return "redirect:/user-details"; // перенаправление пользователя после деактивации
    	    } else {
    	        // Обработка случая, когда пин-код не совпадает
    	        return "redirect:/user-details"; 
    	    }

    	
    }
    
    @GetMapping("/user-details")
    public String showCurrentUserDetails(Model model, RedirectAttributes redirectAttributes) {
        TransferRequestDTO transferRequestDTO = (TransferRequestDTO) model.getAttribute("transferRequestDTO");

        // Что бы получить атрибут из flash attributes
        if (transferRequestDTO == null) {
            transferRequestDTO = (TransferRequestDTO) redirectAttributes.getFlashAttributes().get("transferRequestDTO");
        }

        //Если всё ещё null
        if (transferRequestDTO == null) {
        	 transferRequestDTO = new TransferRequestDTO();
            
        }

        model.addAttribute("transferRequestDTO", transferRequestDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("My name aut = " + (authentication != null ? authentication.getName() : "null"));
        if (authentication != null) {
            Optional<UserEntity> userOptional = userRepo.findByUsername(authentication.getName());

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                List<AccountEntity> bankAccounts = accountRepo.findByUserId(user.getId());

                model.addAttribute("user", user);
                model.addAttribute("bankAccounts", bankAccounts);
            } else {

            }
        }

        return "userDetails";
    }
    
    public UserEntity getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<UserEntity> userOptional = userRepo.findByUsername(authentication.getName());
            return userOptional.orElse(null); // Возвращает объект UserEntity, если он присутствует, в противном случае возвращает null
        }

        return null;
    }
    
    
    @GetMapping("/currency-converter")
    public String showCurrencyConverter(Model model) {
    	 List<CurrencyEntity> currencies = currencyRepo.findAll()
                 .stream()
                 .sorted(Comparator.comparing(CurrencyEntity::getName))
                 .collect(Collectors.toList());
        model.addAttribute("currencies", currencies);
        
        
        // Проверяем, что есть хотя бы одна валюта в списке
        if (!currencies.isEmpty()) {
            // Берем дату из первой валюты в списке
            String lastModificationDate = currencies.get(0).getLastModificationDate();
            model.addAttribute("lastModificationDate", lastModificationDate);
            
        }
        return "currency-converter";
    }
    
    
    @PostMapping("/currency-converter/convert")
    public String convertCurrency(
            @RequestParam("amount") BigDecimal amount,
            @RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency,
            Model model) {

        try {
            BigDecimal result = conversionService.convertCurrency(amount, fromCurrency, toCurrency);
            model.addAttribute("result", result);
        } catch (IllegalArgumentException e) {
            model.addAttribute("result", "Invalid currency codes");
        }

        List<CurrencyEntity> currencies = currencyRepo.findAll();
        
        if (!currencies.isEmpty()) {
            // Берем дату из первой валюты в списке
            String lastModificationDate = currencies.get(0).getLastModificationDate();
            model.addAttribute("lastModificationDate", lastModificationDate);
            
        }
    
        model.addAttribute("currencies", currencies);
        return "currency-converter";
    }
    
    
    
    
    

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }
    

    
    
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userRegistrationDTO") UserRegistrationDTO registrationDTO, Model model,
			BindingResult bindingResult) {

		validationService.pinCodeValidation(registrationDTO.getPincode(), bindingResult);
		validationService.usernameValidation(registrationDTO.getUsername(), bindingResult);
		validationService.passwordValidation(registrationDTO.getPassword(), registrationDTO.getPassword(),
				bindingResult);

		if (bindingResult.hasErrors()) {
			return "register";
		} else {
			UserEntity user = new UserEntity();
			user.setUsername(registrationDTO.getUsername());
			String hashedPassword = passwordEncoder.encode(registrationDTO.getPassword());
			user.setPassword(hashedPassword);
			user.setPincode(registrationDTO.getPincode());
			userRepo.save(user);

			return "redirect:/login";
		}
	}
  
}
