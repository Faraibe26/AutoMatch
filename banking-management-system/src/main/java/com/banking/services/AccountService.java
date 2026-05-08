package com.banking.services;

import com.banking.models.Account;
import com.banking.exceptions.AccountNotFoundException;
import com.banking.exceptions.InsufficientFundsException;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String accountId, String ownerName) {
        Account account = new Account(accountId, ownerName);
        accounts.put(accountId, account);
        return account;
    }

    public void deposit(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountId);
        }
        account.deposit(amount);
    }

    public void withdraw(String accountId, double amount) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountId);
        }
        account.withdraw(amount);
    }

    public void transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = accounts.get(fromAccountId);
        Account toAccount = accounts.get(toAccountId);
        if (fromAccount == null) {
            throw new AccountNotFoundException("Account not found: " + fromAccountId);
        }
        if (toAccount == null) {
            throw new AccountNotFoundException("Account not found: " + toAccountId);
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    public Account getAccount(String accountId) {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountId);
        }
        return account;
    }
}