package com.unitech.UniTech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitech.UniTech.model.TransactionEntity;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long>{
	
	 	List<TransactionEntity> findAll();

	  //  List<TransactionEntity> findBySourceAccountIdOrTargetAccountIdAndSuccessTrue(Long accountId, boolean success);

	   // List<TransactionEntity> findByTargetAccountIdAndSuccessTrue(Long targetAccountId,  boolean success);

	    TransactionEntity save(TransactionEntity transaction);

}
