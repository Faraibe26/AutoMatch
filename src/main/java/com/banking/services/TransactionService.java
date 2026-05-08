package com.banking.services;

import com.banking.models.Account;
import com.banking.models.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing transactions.
 * Implements business logic for transaction queries and reporting.
 * 
 * Single Responsibility: Focuses on transaction-related operations.
 */
public class TransactionService {
    
    /**
     * Gets all transactions for an account.
     *
     * @param account the account
     * @return a list of transactions
     */
    public List<Transaction> getTransactionHistory(Account account) {
        if (account == null) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(account.getTransactionHistory());
    }
    
    /**
     * Gets transactions of a specific type for an account.
     *
     * @param account the account
     * @param type the transaction type to filter by
     * @return a list of transactions of the specified type
     */
    public List<Transaction> getTransactionsByType(Account account, Transaction.TransactionType type) {
        if (account == null || type == null) {
            return new ArrayList<>();
        }
        
        return account.getTransactionHistory().stream()
                .filter(t -> t.getType() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the count of all transactions for an account.
     *
     * @param account the account
     * @return the transaction count
     */
    public int getTransactionCount(Account account) {
        if (account == null) {
            return 0;
        }
        return account.getTransactionHistory().size();
    }
    
    /**
     * Gets the most recent N transactions.
     *
     * @param account the account
     * @param n the number of recent transactions to retrieve
     * @return a list of the most recent N transactions
     */
    public List<Transaction> getRecentTransactions(Account account, int n) {
        if (account == null || n <= 0) {
            return new ArrayList<>();
        }
        
        List<Transaction> transactions = account.getTransactionHistory();
        int startIndex = Math.max(0, transactions.size() - n);
        
        return transactions.subList(startIndex, transactions.size());
    }
    
    /**
     * Prints a formatted transaction history for an account.
     *
     * @param account the account
     */
    public void printTransactionHistory(Account account) {
        if (account == null || account.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }
        
        List<Transaction> transactions = account.getTransactionHistory();
        System.out.println("\n--- Transaction History for Account " + account.getAccountId() + " ---");
        System.out.println("Total Transactions: " + transactions.size());
        System.out.println("-".repeat(80));
        
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
            System.out.println("-".repeat(80));
        }
    }
    
    /**
     * Calculates the total deposits for an account.
     *
     * @param account the account
     * @return the total deposits
     */
    public java.math.BigDecimal getTotalDeposits(Account account) {
        if (account == null) {
            return java.math.BigDecimal.ZERO;
        }
        
        return account.getTransactionHistory().stream()
                .filter(t -> t.getType() == Transaction.TransactionType.DEPOSIT 
                          || t.getType() == Transaction.TransactionType.TRANSFER_IN)
                .map(Transaction::getAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
    
    /**
     * Calculates the total withdrawals for an account.
     *
     * @param account the account
     * @return the total withdrawals
     */
    public java.math.BigDecimal getTotalWithdrawals(Account account) {
        if (account == null) {
            return java.math.BigDecimal.ZERO;
        }
        
        return account.getTransactionHistory().stream()
                .filter(t -> t.getType() == Transaction.TransactionType.WITHDRAWAL 
                          || t.getType() == Transaction.TransactionType.TRANSFER_OUT)
                .map(Transaction::getAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }
}
