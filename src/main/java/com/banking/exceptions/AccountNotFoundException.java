package com.banking.exceptions;

/**
 * Exception thrown when an account cannot be found in the banking system.
 * This exception is part of the custom exception hierarchy for the banking system.
 */
public class AccountNotFoundException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private String accountId;
    
    /**
     * Constructs an AccountNotFoundException with a detailed message.
     *
     * @param message the detail message
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs an AccountNotFoundException with the account ID.
     *
     * @param accountId the account ID that was not found
     */
    public AccountNotFoundException(String accountId, boolean unused) {
        super(String.format("Account with ID '%s' not found in the banking system.", accountId));
        this.accountId = accountId;
    }
    
    public String getAccountId() {
        return accountId;
    }
}
