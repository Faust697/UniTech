package com.unitech.UniTech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitech.UniTech.model.CurrencyEntity;

public interface CurrencyRepo extends JpaRepository<CurrencyEntity, Long> {
    // Найти все валюты
    List<CurrencyEntity> findAll();

    // Найти валюту по названию
    Optional<CurrencyEntity> findByName(String name);

    // Удалить все валюты
    void deleteAll();

    // Сохранить список валют
    CurrencyEntity save(CurrencyEntity currency);
}