package com.unitech.UniTech.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitech.UniTech.model.CurrencyEntity;
import com.unitech.UniTech.repository.CurrencyRepo;

@Service
public class CurrencyUpdateService {

	
	private CurrencyRepo currencyRepo;

	 
	 
    public CurrencyUpdateService(CurrencyRepo currencyRepo) {
		super();
		this.currencyRepo = currencyRepo;
		
	}

    @Async
    @Scheduled(fixedRate = 60000) // Запускать каждую минуту
    public void updateCurrencyRates() throws IOException, Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://currency-conversion-and-exchange-rates.p.rapidapi.com/latest?from=USD"))
                .header("X-RapidAPI-Key", "0e73d85444msh22f8f9a3e472f6dp17a1adjsna702c3e705ed")
                .header("X-RapidAPI-Host", "currency-conversion-and-exchange-rates.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        currencyRepo.deleteAll();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Преобразование строки JSON в объект Map
            Map<String, Object> result = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});

            if (result != null && result.containsKey("rates")) {
                Map<String, Number> rates = (Map<String, Number>) result.get("rates");

                rates.forEach((currencyCode, currencyValue) -> {
                    CurrencyEntity currencyEntity = new CurrencyEntity();
                    currencyEntity.setName(currencyCode);
                    currencyEntity.setCurrencyValue(BigDecimal.valueOf(currencyValue.floatValue()).setScale(3, RoundingMode.HALF_UP).floatValue());
                    currencyEntity.setLastModificationDate(currentTime());

                    currencyRepo.save(currencyEntity);
                });

            
            }

         
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

	
	
	private String currentTime()
	{
		 
		        // Получение текущей даты и времени
		        LocalDateTime currentDateTime = LocalDateTime.now();

		        // Форматирование в строку
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		        String formattedDateTime = currentDateTime.format(formatter);

		        System.out.println("Current Date and Time: " + formattedDateTime);
		    return formattedDateTime;
		
	}
}
