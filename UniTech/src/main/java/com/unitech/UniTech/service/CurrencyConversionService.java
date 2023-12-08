package com.unitech.UniTech.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unitech.UniTech.model.CurrencyEntity;
import com.unitech.UniTech.repository.CurrencyRepo;

//Серивис для конвератиции валют, просто берём валюту, число и перемножаем на модуль
@Service
public class CurrencyConversionService {

    @Autowired
    private CurrencyRepo currencyRepo;

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrencyCode, String toCurrencyCode) {
        Optional<CurrencyEntity> fromCurrency = currencyRepo.findByName(fromCurrencyCode);
        Optional<CurrencyEntity> toCurrency = currencyRepo.findByName(toCurrencyCode);

        if (fromCurrency != null && toCurrency != null) {
            BigDecimal fromRate = BigDecimal.valueOf(fromCurrency.get().getCurrencyValue());
            BigDecimal toRate = BigDecimal.valueOf(toCurrency.get().getCurrencyValue());

            return amount.multiply(toRate).divide(fromRate, RoundingMode.HALF_UP);
        } else {
            throw new IllegalArgumentException("Invalid currency codes");
        }
    }
}
