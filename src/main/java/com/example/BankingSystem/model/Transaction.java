package com.example.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String accountNumber; // The account number associated with this transaction
	private String type; // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER_OUT", "TRANSFER_IN"
	private BigDecimal amount;
	private LocalDateTime timestamp;
	private String description; // Optional: e.g., "Transfer to ACC123", "Deposit cash"

	// Constructor to help create transactions
	public Transaction(String accountNumber, String type, BigDecimal amount, String description) {
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		this.timestamp = LocalDateTime.now();
		this.description = description;
	}

	public Transaction() {
		// Default constructor for JPA
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}