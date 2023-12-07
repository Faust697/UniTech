package com.unitech.UniTech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitech.UniTech.model.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long>{
	
	 // Найти пользователя по ID
    Optional<UserEntity> findById(Long Id);

    // Найти пользователя по имени
    Optional<UserEntity> findByUsername(String username);

    // Найти пользователя по пинкоду
    Optional<UserEntity> findByPincode(String Pincode);

    // Найти всех пользователей
    List<UserEntity> findAll();

    // Сохранить нового пользователя
    UserEntity save(UserEntity user);

	
}
