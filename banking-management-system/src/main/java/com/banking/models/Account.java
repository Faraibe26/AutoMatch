package com.banking.models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountId;
    private String ownerName;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountId, String ownerName) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount, System.currentTimeMillis(), "Deposited " + amount));
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        } else {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount, System.currentTimeMillis(), "Withdrew " + amount));
        }
    }

    public void transfer(Account targetAccount, double amount) {
        this.withdraw(amount);
        targetAccount.deposit(amount);
        transactionHistory.add(new Transaction("Transfer", amount, System.currentTimeMillis(), "Transferred " + amount + " to " + targetAccount.getAccountId()));
    }
}