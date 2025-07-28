package com.example.BankingSystem.service;

import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Transaction; // Import Transaction
import com.example.BankingSystem.repository.AccountRepository;
import com.example.BankingSystem.repository.TransactionRepository; // Assuming you have a TransactionRepository
import jakarta.transaction.Transactional; // Use Jakarta EE's Transactional

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository; // Inject TransactionRepository

	public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@Transactional
	public Account createAccount(Account account) {
		// You might want to add checks here, e.g., if accountNumber already exists
		if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
			throw new IllegalArgumentException("Account with this number already exists.");
		}
		account.setBalance(BigDecimal.ZERO); // Ensure new accounts start with zero balance
		return accountRepository.save(account);
	}

	public Optional<Account> getAccountByAccountNumber(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber);
	}

	public Optional<Account> getAccountDetails(String accountNumber) {
		// For user profile, this might return more detailed account info
		return accountRepository.findByAccountNumber(accountNumber);
	}

	@Transactional
	public void deposit(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Deposit amount must be positive.");
		}

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

		account.setBalance(account.getBalance().add(amount));
		accountRepository.save(account);

		// Record transaction
		transactionRepository.save(new Transaction(accountNumber, "DEPOSIT", amount, "Cash deposit"));
	}

	@Transactional
	public void withdraw(String accountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Withdrawal amount must be positive.");
		}

		Account account = accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

		if (account.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient funds.");
		}

		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);

		// Record transaction
		transactionRepository.save(new Transaction(accountNumber, "WITHDRAWAL", amount, "Cash withdrawal"));
	}

	@Transactional
	public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Transfer amount must be positive.");
		}
		if (fromAccountNumber.equals(toAccountNumber)) {
			throw new IllegalArgumentException("Cannot transfer to the same account.");
		}

		Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Source account not found: " + fromAccountNumber));
		Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Destination account not found: " + toAccountNumber));

		if (fromAccount.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient funds in source account.");
		}

		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
		toAccount.setBalance(toAccount.getBalance().add(amount));

		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);

		// Record transactions for both accounts
		transactionRepository
				.save(new Transaction(fromAccountNumber, "TRANSFER_OUT", amount, "Transfer to " + toAccountNumber));
		transactionRepository
				.save(new Transaction(toAccountNumber, "TRANSFER_IN", amount, "Transfer from " + fromAccountNumber));
	}

	public List<Transaction> getAccountTransactions(String accountNumber) {
		// Ensure the account exists before fetching transactions
		accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

		// Order by timestamp, newest first
		return transactionRepository.findByAccountNumberOrderByTimestampDesc(accountNumber);
	}

    // NEW: Method to get all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll(); // Fetches all accounts from the database
    }
}
