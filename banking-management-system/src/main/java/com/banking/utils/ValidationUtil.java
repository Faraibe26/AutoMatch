package com.banking.utils;

public class ValidationUtil {

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean hasSufficientFunds(double balance, double amount) {
        return balance >= amount;
    }

    public static boolean isValidAccountId(String accountId) {
        return accountId != null && !accountId.trim().isEmpty();
    }

    public static boolean isValidCustomerId(String customerId) {
        return customerId != null && !customerId.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}