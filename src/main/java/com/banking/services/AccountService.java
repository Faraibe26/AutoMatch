package com.banking.services;

import com.banking.models.Account;
import com.banking.models.CheckingAccount;
import com.banking.models.SavingsAccount;
import com.banking.exceptions.*;
import com.banking.utils.ValidationUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing account operations.
 * Implements business logic for accounts using the Service Layer pattern.
 * 
 * Encapsulation: Hides account management details from the UI layer.
 * Single Responsibility: Focuses on account-related operations.
 */
public class AccountService {
    
    private final Map<String, Account> accounts;
    
    /**
     * Constructs an AccountService with an empty account repository.
     */
    public AccountService() {
        this.accounts = new HashMap<>();
    }
    
    /**
     * Creates a new Savings Account.
     *
     * @param accountHolder the account holder's name
     * @param initialBalance the initial deposit amount
     * @return the created account
     * @throws InvalidInputException if inputs are invalid
     */
    public Account createSavingsAccount(String accountHolder, BigDecimal initialBalance) 
            throws InvalidInputException {
        
        ValidationUtil.validateNotEmpty(accountHolder, "Account Holder");
        ValidationUtil.validateNonNegativeAmount(initialBalance, "Initial Balance");
        
        Account account = new SavingsAccount(accountHolder, initialBalance);
        accounts.put(account.getAccountId(), account);
        
        return account;
    }
    
    /**
     * Creates a new Checking Account.
     *
     * @param accountHolder the account holder's name
     * @param initialBalance the initial deposit amount
     * @param overdraftProtection whether to enable overdraft protection
     * @return the created account
     * @throws InvalidInputException if inputs are invalid
     */
    public Account createCheckingAccount(String accountHolder, BigDecimal initialBalance, 
                                         boolean overdraftProtection) 
            throws InvalidInputException {
        
        ValidationUtil.validateNotEmpty(accountHolder, "Account Holder");
        ValidationUtil.validateNonNegativeAmount(initialBalance, "Initial Balance");
        
        Account account = new CheckingAccount(accountHolder, initialBalance, overdraftProtection);
        accounts.put(account.getAccountId(), account);
        
        return account;
    }
    
    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the account ID
     * @return the account
     * @throws AccountNotFoundException if account is not found
     */
    public Account getAccount(String accountId) throws AccountNotFoundException {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId, true);
        }
        return account;
    }
    
    /**
     * Gets all accounts in the system.
     *
     * @return a map of all accounts
     */
    public Map<String, Account> getAllAccounts() {
        return new HashMap<>(accounts);
    }
    
    /**
     * Deposits money into an account.
     *
     * @param accountId the account ID
     * @param amount the deposit amount
     * @throws AccountNotFoundException if account is not found
     * @throws InvalidInputException if amount is invalid
     */
    public void deposit(String accountId, BigDecimal amount) 
            throws AccountNotFoundException, InvalidInputException {
        
        Account account = getAccount(accountId);
        account.deposit(amount);
    }
    
    /**
     * Withdraws money from an account.
     *
     * @param accountId the account ID
     * @param amount the withdrawal amount
     * @throws AccountNotFoundException if account is not found
     * @throws InvalidInputException if amount is invalid
     * @throws InsufficientFundsException if balance is insufficient
     */
    public void withdraw(String accountId, BigDecimal amount) 
            throws AccountNotFoundException, InvalidInputException, InsufficientFundsException {
        
        Account account = getAccount(accountId);
        account.withdraw(amount);
    }
    
    /**
     * Transfers money between two accounts.
     *
     * @param fromAccountId the sender's account ID
     * @param toAccountId the recipient's account ID
     * @param amount the transfer amount
     * @throws AccountNotFoundException if either account is not found
     * @throws InvalidInputException if amount is invalid
     * @throws InsufficientFundsException if sender's balance is insufficient
     */
    public void transfer(String fromAccountId, String toAccountId, BigDecimal amount) 
            throws AccountNotFoundException, InvalidInputException, InsufficientFundsException {
        
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);
        
        fromAccount.transfer(amount, toAccount);
    }
    
    /**
     * Closes (deletes) an account.
     *
     * @param accountId the account ID to close
     * @throws AccountNotFoundException if account is not found
     */
    public void closeAccount(String accountId) throws AccountNotFoundException {
        Account account = getAccount(accountId);
        accounts.remove(accountId);
    }
    
    /**
     * Gets the total balance across all accounts.
     *
     * @return the total balance
     */
    public BigDecimal getTotalBalance() {
        return accounts.values().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Gets the number of accounts in the system.
     *
     * @return the account count
     */
    public int getAccountCount() {
        return accounts.size();
    }
    
    /**
     * Checks if an account exists.
     *
     * @param accountId the account ID
     * @return true if the account exists, false otherwise
     */
    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }
}
