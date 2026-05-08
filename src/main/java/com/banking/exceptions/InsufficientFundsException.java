package com.banking.exceptions;

/**
 * Exception thrown when attempting to withdraw more funds than available in an account.
 * This exception is part of the custom exception hierarchy for the banking system.
 */
public class InsufficientFundsException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private String accountId;
    private java.math.BigDecimal requestedAmount;
    private java.math.BigDecimal availableBalance;
    
    /**
     * Constructs an InsufficientFundsException with a detailed message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    /**
     * Constructs an InsufficientFundsException with account details.
     *
     * @param accountId the account ID
     * @param requestedAmount the amount requested for withdrawal
     * @param availableBalance the current account balance
     */
    public InsufficientFundsException(String accountId, java.math.BigDecimal requestedAmount, 
                                      java.math.BigDecimal availableBalance) {
        super(String.format("Insufficient funds in account %s. Requested: $%s, Available: $%s",
                accountId, requestedAmount, availableBalance));
        this.accountId = accountId;
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public java.math.BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    
    public java.math.BigDecimal getAvailableBalance() {
        return availableBalance;
    }
}
