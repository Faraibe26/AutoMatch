package com.banking.models;

import java.math.BigDecimal;

/**
 * Represents a Checking Account with unlimited withdrawals.
 * Demonstrates inheritance and polymorphism by extending Account.
 * 
 * A checking account may have an overdraft fee for negative balance situations.
 */
public class CheckingAccount extends Account {
    
    private static final BigDecimal OVERDRAFT_FEE = new BigDecimal("35.00");
    private boolean overdraftProtection;
    
    /**
     * Constructs a CheckingAccount with initial details.
     *
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial deposit amount
     */
    public CheckingAccount(String accountHolder, BigDecimal initialBalance) {
        super(accountHolder, initialBalance);
        this.overdraftProtection = false;
    }
    
    /**
     * Constructs a CheckingAccount with overdraft protection option.
     *
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial deposit amount
     * @param overdraftProtection whether overdraft protection is enabled
     */
    public CheckingAccount(String accountHolder, BigDecimal initialBalance, boolean overdraftProtection) {
        super(accountHolder, initialBalance);
        this.overdraftProtection = overdraftProtection;
    }
    
    /**
     * Performs account-specific withdrawal logic for checking account.
     * In this case, there are no specific restrictions (unlimited withdrawals).
     *
     * @param amount the amount being withdrawn
     */
    @Override
    protected void performWithdrawal(BigDecimal amount) {
        // No additional restrictions for checking account
        // The standard validation in the parent class is sufficient
    }
    
    /**
     * Enables or disables overdraft protection.
     *
     * @param enabled true to enable overdraft protection
     */
    public void setOverdraftProtection(boolean enabled) {
        this.overdraftProtection = enabled;
    }
    
    /**
     * Checks if overdraft protection is enabled.
     *
     * @return true if overdraft protection is enabled
     */
    public boolean hasOverdraftProtection() {
        return overdraftProtection;
    }
    
    /**
     * Gets the overdraft fee amount.
     *
     * @return the overdraft fee
     */
    public BigDecimal getOverdraftFee() {
        return OVERDRAFT_FEE;
    }
    
    @Override
    public String getAccountType() {
        return "Checking Account";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               String.format("%nAccount Type Benefits:%n  - Unlimited Withdrawals%n  - Overdraft Protection: %s%n  - Overdraft Fee: $%s",
                   overdraftProtection ? "Enabled" : "Disabled", OVERDRAFT_FEE);
    }
}
