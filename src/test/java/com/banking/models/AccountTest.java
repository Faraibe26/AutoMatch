package com.banking.models;

import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Unit tests for Account models (SavingsAccount and CheckingAccount).
 * Tests core account functionality including deposits, withdrawals, and transfers.
 */
public class AccountTest {
    
    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;
    
    @Before
    public void setUp() {
        savingsAccount = new SavingsAccount("John Doe", new BigDecimal("1000.00"));
        checkingAccount = new CheckingAccount("Jane Smith", new BigDecimal("500.00"));
    }
    
    // Savings Account Tests
    
    @Test
    public void testSavingsAccountCreation() {
        assertNotNull(savingsAccount.getAccountId());
        assertEquals("John Doe", savingsAccount.getAccountHolder());
        assertEquals(new BigDecimal("1000.00"), savingsAccount.getBalance());
        assertEquals("Savings Account", savingsAccount.getAccountType());
    }
    
    @Test
    public void testSavingsAccountDeposit() throws InvalidInputException {
        savingsAccount.deposit(new BigDecimal("500.00"));
        assertEquals(new BigDecimal("1500.00"), savingsAccount.getBalance());
        assertEquals(2, savingsAccount.getTransactionHistory().size());
    }
    
    @Test(expected = InvalidInputException.class)
    public void testSavingsAccountNegativeDeposit() throws InvalidInputException {
        savingsAccount.deposit(new BigDecimal("-100.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testSavingsAccountZeroDeposit() throws InvalidInputException {
        savingsAccount.deposit(BigDecimal.ZERO);
    }
    
    @Test
    public void testSavingsAccountWithdraw() throws InvalidInputException, InsufficientFundsException {
        savingsAccount.withdraw(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("800.00"), savingsAccount.getBalance());
    }
    
    @Test(expected = InsufficientFundsException.class)
    public void testSavingsAccountInsufficientFunds() throws InvalidInputException, InsufficientFundsException {
        savingsAccount.withdraw(new BigDecimal("2000.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testSavingsAccountNegativeWithdraw() throws InvalidInputException, InsufficientFundsException {
        savingsAccount.withdraw(new BigDecimal("-100.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testSavingsAccountMinimumBalanceViolation() throws InvalidInputException, InsufficientFundsException {
        // Try to withdraw more than balance - $100 minimum
        savingsAccount.withdraw(new BigDecimal("950.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testSavingsAccountMonthlyWithdrawalLimit() throws InvalidInputException, InsufficientFundsException {
        // Attempt 7 withdrawals (limit is 6)
        for (int i = 0; i < 7; i++) {
            savingsAccount.withdraw(new BigDecimal("50.00"));
        }
    }
    
    @Test
    public void testSavingsAccountRemainingWithdrawals() throws InvalidInputException, InsufficientFundsException {
        assertEquals(6, savingsAccount.getRemainingMonthlyWithdrawals());
        savingsAccount.withdraw(new BigDecimal("50.00"));
        assertEquals(5, savingsAccount.getRemainingMonthlyWithdrawals());
    }
    
    @Test
    public void testSavingsAccountResetMonthlyWithdrawals() throws InvalidInputException, InsufficientFundsException {
        savingsAccount.withdraw(new BigDecimal("50.00"));
        savingsAccount.withdraw(new BigDecimal("50.00"));
        assertEquals(4, savingsAccount.getRemainingMonthlyWithdrawals());
        
        savingsAccount.resetMonthlyWithdrawalCount();
        assertEquals(6, savingsAccount.getRemainingMonthlyWithdrawals());
    }
    
    // Checking Account Tests
    
    @Test
    public void testCheckingAccountCreation() {
        assertNotNull(checkingAccount.getAccountId());
        assertEquals("Jane Smith", checkingAccount.getAccountHolder());
        assertEquals(new BigDecimal("500.00"), checkingAccount.getBalance());
        assertEquals("Checking Account", checkingAccount.getAccountType());
    }
    
    @Test
    public void testCheckingAccountUnlimitedWithdrawals() throws InvalidInputException, InsufficientFundsException {
        for (int i = 0; i < 10; i++) {
            checkingAccount.withdraw(new BigDecimal("10.00"));
        }
        assertEquals(new BigDecimal("400.00"), checkingAccount.getBalance());
    }
    
    @Test
    public void testCheckingAccountOverdraftProtection() {
        assertFalse(checkingAccount.hasOverdraftProtection());
        checkingAccount.setOverdraftProtection(true);
        assertTrue(checkingAccount.hasOverdraftProtection());
    }
    
    @Test
    public void testCheckingAccountOverdraftFee() {
        assertEquals(new BigDecimal("35.00"), checkingAccount.getOverdraftFee());
    }
    
    // Transfer Tests
    
    @Test
    public void testTransferBetweenAccounts() throws InvalidInputException, InsufficientFundsException {
        savingsAccount.transfer(new BigDecimal("200.00"), checkingAccount);
        
        assertEquals(new BigDecimal("800.00"), savingsAccount.getBalance());
        assertEquals(new BigDecimal("700.00"), checkingAccount.getBalance());
    }
    
    @Test(expected = InsufficientFundsException.class)
    public void testTransferInsufficientFunds() throws InvalidInputException, InsufficientFundsException {
        checkingAccount.transfer(new BigDecimal("1000.00"), savingsAccount);
    }
    
    @Test
    public void testTransferRecordsInHistory() throws InvalidInputException, InsufficientFundsException {
        int initialTransactions = savingsAccount.getTransactionHistory().size();
        
        savingsAccount.transfer(new BigDecimal("100.00"), checkingAccount);
        
        assertEquals(initialTransactions + 1, savingsAccount.getTransactionHistory().size());
        assertEquals(savingsAccount.getTransactionHistory().size() - 1 + 1, 
                checkingAccount.getTransactionHistory().size());
    }
    
    // Transaction History Tests
    
    @Test
    public void testTransactionHistoryRecording() throws InvalidInputException, InsufficientFundsException {
        int initialSize = savingsAccount.getTransactionHistory().size();
        
        savingsAccount.deposit(new BigDecimal("100.00"));
        assertEquals(initialSize + 1, savingsAccount.getTransactionHistory().size());
        
        savingsAccount.withdraw(new BigDecimal("50.00"));
        assertEquals(initialSize + 2, savingsAccount.getTransactionHistory().size());
    }
    
    @Test
    public void testTransactionDetails() throws InvalidInputException {
        savingsAccount.deposit(new BigDecimal("250.00"));
        
        Transaction lastTransaction = savingsAccount.getTransactionHistory()
                .get(savingsAccount.getTransactionHistory().size() - 1);
        
        assertEquals(Transaction.TransactionType.DEPOSIT, lastTransaction.getType());
        assertEquals(new BigDecimal("250.00"), lastTransaction.getAmount());
        assertNotNull(lastTransaction.getTimestamp());
        assertNotNull(lastTransaction.getTransactionId());
    }
}
