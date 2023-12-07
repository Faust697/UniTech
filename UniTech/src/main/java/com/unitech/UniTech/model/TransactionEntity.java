package com.unitech.UniTech.model;



	import java.math.BigDecimal;
	import java.time.LocalDateTime;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;

	@Entity
	public class TransactionEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String sourceAccountId;
	    private String targetAccountId;
	    private Float amount;
	    private String currency;
	    private LocalDateTime timestamp;
	    private boolean success;

	    // Другие необходимые поля и методы

	    public TransactionEntity() {
	    }

	    public TransactionEntity(
	    		String sourceAccountId,
	    		String targetAccountId,
	    		Float amount,
	    		String currency, 
	    		LocalDateTime timestamp,
	    		boolean success) {
	    	
	        this.sourceAccountId = sourceAccountId;
	        this.targetAccountId = targetAccountId;
	        this.amount = amount;
	        this.currency = currency;
	        this.timestamp = timestamp;
	        this.success = success;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSourceAccountId() {
			return sourceAccountId;
		}

		public void setSourceAccountId(String sourceAccountId) {
			this.sourceAccountId = sourceAccountId;
		}

		public String getTargetAccountId() {
			return targetAccountId;
		}

		public void setTargetAccountId(String targetAccountId) {
			this.targetAccountId = targetAccountId;
		}

		public Float getAmount() {
			return amount;
		}

		public void setAmount(Float amount) {
			this.amount = amount;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

	}

	
	

