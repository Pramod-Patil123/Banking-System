package com.example.BankingSystem.service; // Note the 'BankingSystem' capitalization

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Transaction;
import com.example.BankingSystem.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public Transaction createTransaction(Account account, String type, BigDecimal amount, String description,
			String relatedAccountNumber) {
		Transaction transaction = new Transaction();
		//transaction.setAccountNumber(account);
		//transaction.setTransactionType(type);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		//transaction.setRelatedAccountNumber(relatedAccountNumber);
		return transactionRepository.save(transaction);
	}

	//public List<Transaction> getTransactionsByAccountId(Long accountId) {
		// CORRECTION: Call the method on the 'transactionRepository' instance, not the
		// class 'TransactionRepository'
		//return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);

	}
//}