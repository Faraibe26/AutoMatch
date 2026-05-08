package com.banking.services;

import com.banking.models.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private List<Transaction> transactionHistory;

    public TransactionService() {
        this.transactionHistory = new ArrayList<>();
    }

    public void logTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}