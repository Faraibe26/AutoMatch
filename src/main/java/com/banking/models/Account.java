package com.banking.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class representing a bank account.
 * Uses inheritance and abstraction to define common account behavior.
 * 
 * Encapsulation: All fields are private with controlled access via getters.
 * Inheritance: Subclasses (SavingsAccount, CheckingAccount) extend this class.
 * Abstraction: Abstract methods for account-specific operations.
 */
public abstract class Account {
    
    private final String accountId;
    private final String accountHolder;
    private BigDecimal balance;
    private final List<Transaction> transactionHistory;
    private static int accountCounter = 1000;
    
    /**
     * Constructs an Account with initial details.
     *
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial deposit amount
     */
    protected Account(String accountHolder, BigDecimal initialBalance) {
        this.accountId = generateAccountId();
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        
        // Record initial deposit
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            this.transactionHistory.add(
                new Transaction(Transaction.TransactionType.DEPOSIT, initialBalance, "Account opened with initial deposit")
            );
        }
    }
    
    /**
     * Generates a unique account ID.
     *
     * @return a unique account ID
     */
    private synchronized static String generateAccountId() {
        return "ACC-" + System.currentTimeMillis() + "-" + (++accountCounter);
    }
    
    /**
     * Deposits money into the account.
     * This is a concrete method with validation logic.
     *
     * @param amount the amount to deposit
     * @throws com.banking.exceptions.InvalidInputException if amount is invalid
     */
    public final void deposit(BigDecimal amount) throws com.banking.exceptions.InvalidInputException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new com.banking.exceptions.InvalidInputException(
                "deposit amount", 
                amount != null ? amount.toString() : "null",
                "Amount must be greater than zero"
            );
        }
        
        this.balance = this.balance.add(amount);
        this.transactionHistory.add(
            new Transaction(Transaction.TransactionType.DEPOSIT, amount, "Deposit to account")
        );
    }
    
    /**
     * Withdraws money from the account.
     * This is a concrete method with validation logic.
     *
     * @param amount the amount to withdraw
     * @throws com.banking.exceptions.InvalidInputException if amount is invalid
     * @throws com.banking.exceptions.InsufficientFundsException if balance is insufficient
     */
    public final void withdraw(BigDecimal amount) 
            throws com.banking.exceptions.InvalidInputException, 
                   com.banking.exceptions.InsufficientFundsException {
        
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new com.banking.exceptions.InvalidInputException(
                "withdrawal amount",
                amount != null ? amount.toString() : "null",
                "Amount must be greater than zero"
            );
        }
        
        if (amount.compareTo(this.balance) > 0) {
            throw new com.banking.exceptions.InsufficientFundsException(
                this.accountId, amount, this.balance
            );
        }
        
        // Call abstract method for account-specific withdrawal logic
        try {
            performWithdrawal(amount);
        } catch (com.banking.exceptions.InvalidInputException e) {
            throw e;
        }
        
        this.balance = this.balance.subtract(amount);
        this.transactionHistory.add(
            new Transaction(Transaction.TransactionType.WITHDRAWAL, amount, "Withdrawal from account")
        );
    }
    
    /**
     * Abstract method for account-specific withdrawal logic.
     * Subclasses can override to implement account-specific rules.
     *
     * @param amount the amount being withdrawn
     * @throws com.banking.exceptions.InvalidInputException if withdrawal violates account rules
     */
    protected abstract void performWithdrawal(BigDecimal amount) 
            throws com.banking.exceptions.InvalidInputException;
    
    /**
     * Transfers money to another account.
     *
     * @param amount the amount to transfer
     * @param recipientAccount the recipient account
     * @throws com.banking.exceptions.InvalidInputException if amount is invalid
     * @throws com.banking.exceptions.InsufficientFundsException if balance is insufficient
     */
    public void transfer(BigDecimal amount, Account recipientAccount) 
            throws com.banking.exceptions.InvalidInputException,
                   com.banking.exceptions.InsufficientFundsException {
        
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new com.banking.exceptions.InvalidInputException(
                "transfer amount",
                amount != null ? amount.toString() : "null",
                "Amount must be greater than zero"
            );
        }
        
        if (amount.compareTo(this.balance) > 0) {
            throw new com.banking.exceptions.InsufficientFundsException(
                this.accountId, amount, this.balance
            );
        }
        
        this.balance = this.balance.subtract(amount);
        this.transactionHistory.add(
            new Transaction(
                Transaction.TransactionType.TRANSFER_OUT, 
                amount, 
                "Transfer to account " + recipientAccount.getAccountId(),
                recipientAccount.getAccountId()
            )
        );
        
        recipientAccount.balance = recipientAccount.balance.add(amount);
        recipientAccount.transactionHistory.add(
            new Transaction(
                Transaction.TransactionType.TRANSFER_IN,
                amount,
                "Transfer from account " + this.getAccountId(),
                this.getAccountId()
            )
        );
    }
    
    // Getters (Encapsulation)
    public String getAccountId() {
        return accountId;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
    
    /**
     * Abstract method to get the account type.
     *
     * @return the account type
     */
    public abstract String getAccountType();
    
    /**
     * Returns a detailed string representation of the account.
     *
     * @return formatted account details
     */
    @Override
    public String toString() {
        return String.format(
            "Account ID: %s%nAccount Holder: %s%nAccount Type: %s%nBalance: $%s%nTransactions: %d",
            accountId, accountHolder, getAccountType(), balance, transactionHistory.size()
        );
    }
}
