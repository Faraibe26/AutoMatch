package com.banking.models;

import java.math.BigDecimal;

/**
 * Represents a Savings Account with specific withdrawal restrictions.
 * Demonstrates inheritance and polymorphism by extending Account.
 * 
 * A savings account has a monthly withdrawal limit.
 */
public class SavingsAccount extends Account {
    
    private static final int MAX_MONTHLY_WITHDRAWALS = 6;
    private int monthlyWithdrawalCount;
    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("100.00");
    
    /**
     * Constructs a SavingsAccount with initial details.
     *
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial deposit amount
     */
    public SavingsAccount(String accountHolder, BigDecimal initialBalance) {
        super(accountHolder, initialBalance);
        this.monthlyWithdrawalCount = 0;
    }
    
    /**
     * Performs account-specific withdrawal logic for savings account.
     * Enforces the monthly withdrawal limit and minimum balance requirement.
     *
     * @param amount the amount being withdrawn
     * @throws com.banking.exceptions.InvalidInputException if withdrawal limit exceeded or minimum balance violated
     */
    @Override
    protected void performWithdrawal(BigDecimal amount) 
            throws com.banking.exceptions.InvalidInputException {
        
        if (monthlyWithdrawalCount >= MAX_MONTHLY_WITHDRAWALS) {
            throw new com.banking.exceptions.InvalidInputException(
                "withdrawal",
                String.valueOf(monthlyWithdrawalCount),
                "Monthly withdrawal limit of " + MAX_MONTHLY_WITHDRAWALS + " exceeded"
            );
        }
        
        if (getBalance().subtract(amount).compareTo(MINIMUM_BALANCE) < 0) {
            throw new com.banking.exceptions.InvalidInputException(
                "withdrawal",
                amount.toString(),
                "Cannot withdraw below minimum balance of $" + MINIMUM_BALANCE
            );
        }
        
        monthlyWithdrawalCount++;
    }
    
    /**
     * Resets the monthly withdrawal counter (called at the beginning of each month).
     */
    public void resetMonthlyWithdrawalCount() {
        this.monthlyWithdrawalCount = 0;
    }
    
    /**
     * Gets the remaining withdrawals for the month.
     *
     * @return remaining withdrawals allowed
     */
    public int getRemainingMonthlyWithdrawals() {
        return MAX_MONTHLY_WITHDRAWALS - monthlyWithdrawalCount;
    }
    
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               String.format("%nAccount Type Benefits:%n  - Monthly Withdrawal Limit: %d%n  - Remaining Withdrawals: %d%n  - Minimum Balance Required: $%s",
                   MAX_MONTHLY_WITHDRAWALS, getRemainingMonthlyWithdrawals(), MINIMUM_BALANCE);
    }
}
