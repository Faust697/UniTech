package com.unitech.UniTech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitech.UniTech.model.AccountEntity;

public interface AccountRepo extends JpaRepository<AccountEntity, Long>{
	
	Optional<AccountEntity> findByName(String name);
	 
	Optional<AccountEntity> findById(Long id);

    List<AccountEntity> findByUserId(Long userId);

    AccountEntity save(AccountEntity account);
}
