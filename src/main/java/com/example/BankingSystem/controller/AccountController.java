package com.example.BankingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.BankingSystem.model.Account;
import com.example.BankingSystem.model.Transaction;
import com.example.BankingSystem.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// --- Main Landing Page ---
	@GetMapping // Handles http://localhost:8080/accounts
	public String listAccounts(Model model) {
		// This will be our landing page, showing basic options like the image
		// You might want to add some general info or links here.
		return "index"; // Corresponds to src/main/resources/templates/index.html
	}

	
	// LOGIN (Placeholder)
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Corresponds to src/main/resources/templates/login.html
    }

    // LOGOUT (Placeholder)
    // In a real app, this would typically be a POST request handled by Spring Security
    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        // In a real application, you'd invalidate the session here
        redirectAttributes.addFlashAttribute("message", "You have been logged out.");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/accounts"; // Redirect to home or login page
    }
	// --- Create Account ---
	@GetMapping("/create") // Shows the form to create an account
	public String showCreateAccountForm(Model model) {
		model.addAttribute("account", new Account());
		return "create-account"; // Template: create-account.html
	}

	@PostMapping("/create") // Processes the form submission for creating an account
	public String createAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes) {
		try {
			// Generate a simple account number (you might want a more robust solution)
			String newAccountNumber = "ACC" + System.currentTimeMillis();
			account.setAccountNumber(newAccountNumber);
			account.setBalance(BigDecimal.ZERO); // Initialize balance to zero
			accountService.createAccount(account);
			redirectAttributes.addFlashAttribute("message",
					"Account created successfully with Account Number: " + newAccountNumber);
			redirectAttributes.addFlashAttribute("messageType", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error creating account: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
		}
		return "redirect:/accounts"; // Redirect to the main dashboard/home
	}

	// --- Deposit ---
    @GetMapping("/deposit")
    public String showDepositForm() {
        return "deposit"; // This should render src/main/resources/templates/deposit.html
    }
	
	
	@PostMapping("/deposit")
	public String deposit(@RequestParam String accountNumber, @RequestParam BigDecimal amount,
			RedirectAttributes redirectAttributes) {
		try {
			accountService.deposit(accountNumber, amount);
			redirectAttributes.addFlashAttribute("message", "Deposit successful!");
			redirectAttributes.addFlashAttribute("messageType", "success");
		} catch (IllegalArgumentException e) { // Catch specific exception
			redirectAttributes.addFlashAttribute("message", "Deposit failed: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
		}
		return "redirect:/accounts";
	}

	// --- Withdraw ---
	@GetMapping("/withdraw") // Shows the form for withdrawing funds
	public String showWithdrawForm() {
		return "withdraw"; // Template: withdraw.html
	}

	@PostMapping("/withdraw")
	public String withdraw(@RequestParam String accountNumber, @RequestParam BigDecimal amount,
			RedirectAttributes redirectAttributes) {
		try {
			accountService.withdraw(accountNumber, amount);
			redirectAttributes.addFlashAttribute("message", "Withdrawal successful!");
			redirectAttributes.addFlashAttribute("messageType", "success");
		} catch (IllegalArgumentException e) { // Catch specific exception
			redirectAttributes.addFlashAttribute("message", "Withdrawal failed: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
		}
		return "redirect:/accounts";
	}

	// --- 1. Check Balance ---
	@GetMapping("/check-balance") // Shows the form to input account number for balance check
	public String showCheckBalanceForm() {
		return "check-balance"; // Template: check-balance.html
	}

	@PostMapping("/check-balance-result") // Processes the form submission to show balance
	public String checkBalance(@RequestParam String accountNumber, Model model, RedirectAttributes redirectAttributes) {
		try {
			Optional<Account> accountOptional = accountService.getAccountByAccountNumber(accountNumber);
			if (accountOptional.isPresent()) {
				model.addAttribute("account", accountOptional.get());
				return "balance-result"; // Template: balance-result.html (displays balance)
			} else {
				redirectAttributes.addFlashAttribute("message", "Account not found with number: " + accountNumber);
				redirectAttributes.addFlashAttribute("messageType", "error");
				return "redirect:/accounts/check-balance"; // Redirect back to the form with an error
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error checking balance: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/accounts/check-balance"; // Redirect back to the form with an error
		}
	}

	// --- 2. Transaction History ---
	@GetMapping("/transaction-history") // Shows the form to input account number for history
	public String showTransactionHistoryForm() {
		return "transaction-history-form"; // Template: transaction-history-form.html
	}

	@PostMapping("/transaction-history-result") // Processes the form submission to show history
	public String getTransactionHistory(@RequestParam String accountNumber, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			// Get account details (for display on the history page)
			Account account = accountService.getAccountByAccountNumber(accountNumber)
					.orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

			List<Transaction> transactions = accountService.getAccountTransactions(accountNumber);
			model.addAttribute("account", account); // Pass account for context
			model.addAttribute("transactions", transactions);
			return "transaction-history-result"; // Template: transaction-history-result.html
		} catch (IllegalArgumentException e) { // Catch specific exception
			redirectAttributes.addFlashAttribute("message", "Error fetching transaction history: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/accounts/transaction-history"; // Redirect back to form
		} catch (Exception e) { // Catch any other unexpected errors
			redirectAttributes.addFlashAttribute("message", "An unexpected error occurred: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/accounts/transaction-history"; // Redirect back to form
		}
	}

	// --- 3. Transfer Money ---
	@GetMapping("/transfer") // Shows the form for transferring money
	public String showTransferMoneyForm() {
		return "transfer-money"; // Template: transfer-money.html
	}

	@PostMapping("/transfer")
	public String transferMoney(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
			@RequestParam BigDecimal amount, RedirectAttributes redirectAttributes) {
		try {
			accountService.transferMoney(fromAccountNumber, toAccountNumber, amount);
			redirectAttributes.addFlashAttribute("message", "Money transferred successfully!");
			redirectAttributes.addFlashAttribute("messageType", "success");
		} catch (IllegalArgumentException e) { // Catch specific exception
			redirectAttributes.addFlashAttribute("message", "Transfer failed: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
		}
		return "redirect:/accounts";
	}

	// --- 4. User Profile ---
	@GetMapping("/user-profile") // Shows the form to input account number for user profile
	public String showUserProfileForm() {
		return "user-profile-form"; // Template: user-profile-form.html
	}

	@PostMapping("/user-profile-details") // Processes the form submission to show user profile
	public String getUserProfile(@RequestParam String accountNumber, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Optional<Account> accountOptional = accountService.getAccountDetails(accountNumber);
			if (accountOptional.isPresent()) {
				model.addAttribute("account", accountOptional.get());
				return "user-profile-details"; // Template: user-profile-details.html (displays profile)
			} else {
				redirectAttributes.addFlashAttribute("message",
						"User profile not found for account number: " + accountNumber);
				redirectAttributes.addFlashAttribute("messageType", "error");
				return "redirect:/accounts/user-profile"; // Redirect back to the form with an error
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error fetching user profile: " + e.getMessage());
			redirectAttributes.addFlashAttribute("messageType", "error");
			return "redirect:/accounts/user-profile"; // Redirect back to the form with an error
		}
	}

    // --- Get All Account Details ---
    @GetMapping("/details") // Endpoint to show all accounts
    public String showAccountDetails(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        model.addAttribute("accountCount", accounts.size()); // Pass account count
        return "account-details"; // Template: account-details.html
    }
}
