package com.banking.services;

import com.banking.exceptions.AccountNotFoundException;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidInputException;
import com.banking.models.Account;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Unit tests for AccountService.
 * Tests service layer operations for account management.
 */
public class AccountServiceTest {
    
    private AccountService accountService;
    
    @Before
    public void setUp() {
        accountService = new AccountService();
    }
    
    // Account Creation Tests
    
    @Test
    public void testCreateSavingsAccount() throws InvalidInputException {
        Account account = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        
        assertNotNull(account);
        assertEquals("John Doe", account.getAccountHolder());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
        assertEquals("Savings Account", account.getAccountType());
    }
    
    @Test
    public void testCreateCheckingAccount() throws InvalidInputException {
        Account account = accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        
        assertNotNull(account);
        assertEquals("Jane Smith", account.getAccountHolder());
        assertEquals(new BigDecimal("500.00"), account.getBalance());
        assertEquals("Checking Account", account.getAccountType());
    }
    
    @Test(expected = InvalidInputException.class)
    public void testCreateAccountWithEmptyName() throws InvalidInputException {
        accountService.createSavingsAccount("", new BigDecimal("1000.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testCreateAccountWithNegativeBalance() throws InvalidInputException {
        accountService.createSavingsAccount("John Doe", new BigDecimal("-100.00"));
    }
    
    // Account Retrieval Tests
    
    @Test
    public void testGetAccount() throws InvalidInputException, AccountNotFoundException {
        Account created = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        Account retrieved = accountService.getAccount(created.getAccountId());
        
        assertEquals(created.getAccountId(), retrieved.getAccountId());
        assertEquals("John Doe", retrieved.getAccountHolder());
    }
    
    @Test(expected = AccountNotFoundException.class)
    public void testGetNonExistentAccount() throws AccountNotFoundException {
        accountService.getAccount("INVALID-ACCOUNT-ID");
    }
    
    @Test
    public void testGetAllAccounts() throws InvalidInputException {
        assertEquals(0, accountService.getAllAccounts().size());
        
        accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), true);
        
        assertEquals(2, accountService.getAllAccounts().size());
    }
    
    // Deposit Tests
    
    @Test
    public void testDeposit() throws InvalidInputException, AccountNotFoundException {
        Account account = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        
        accountService.deposit(account.getAccountId(), new BigDecimal("500.00"));
        
        Account updatedAccount = accountService.getAccount(account.getAccountId());
        assertEquals(new BigDecimal("1500.00"), updatedAccount.getBalance());
    }
    
    @Test(expected = AccountNotFoundException.class)
    public void testDepositToNonExistentAccount() throws AccountNotFoundException, InvalidInputException {
        accountService.deposit("INVALID-ACCOUNT-ID", new BigDecimal("500.00"));
    }
    
    @Test(expected = InvalidInputException.class)
    public void testDepositNegativeAmount() throws InvalidInputException, AccountNotFoundException {
        Account account = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        accountService.deposit(account.getAccountId(), new BigDecimal("-500.00"));
    }
    
    // Withdraw Tests
    
    @Test
    public void testWithdraw() throws InvalidInputException, AccountNotFoundException, InsufficientFundsException {
        Account account = accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        
        accountService.withdraw(account.getAccountId(), new BigDecimal("200.00"));
        
        Account updatedAccount = accountService.getAccount(account.getAccountId());
        assertEquals(new BigDecimal("300.00"), updatedAccount.getBalance());
    }
    
    @Test(expected = InsufficientFundsException.class)
    public void testWithdrawInsufficientFunds() throws InvalidInputException, AccountNotFoundException, InsufficientFundsException {
        Account account = accountService.createCheckingAccount("Jane Smith", new BigDecimal("100.00"), false);
        accountService.withdraw(account.getAccountId(), new BigDecimal("500.00"));
    }
    
    // Transfer Tests
    
    @Test
    public void testTransfer() throws InvalidInputException, AccountNotFoundException, InsufficientFundsException {
        Account sender = accountService.createCheckingAccount("John Doe", new BigDecimal("1000.00"), false);
        Account recipient = accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        
        accountService.transfer(sender.getAccountId(), recipient.getAccountId(), new BigDecimal("300.00"));
        
        Account updatedSender = accountService.getAccount(sender.getAccountId());
        Account updatedRecipient = accountService.getAccount(recipient.getAccountId());
        
        assertEquals(new BigDecimal("700.00"), updatedSender.getBalance());
        assertEquals(new BigDecimal("800.00"), updatedRecipient.getBalance());
    }
    
    @Test(expected = AccountNotFoundException.class)
    public void testTransferFromNonExistentAccount() throws InvalidInputException, AccountNotFoundException, InsufficientFundsException {
        Account recipient = accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        accountService.transfer("INVALID-ACCOUNT-ID", recipient.getAccountId(), new BigDecimal("100.00"));
    }
    
    // Account Management Tests
    
    @Test
    public void testAccountExists() throws InvalidInputException {
        Account account = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        
        assertTrue(accountService.accountExists(account.getAccountId()));
        assertFalse(accountService.accountExists("INVALID-ACCOUNT-ID"));
    }
    
    @Test
    public void testGetAccountCount() throws InvalidInputException {
        assertEquals(0, accountService.getAccountCount());
        
        accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        assertEquals(1, accountService.getAccountCount());
        
        accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        assertEquals(2, accountService.getAccountCount());
    }
    
    @Test
    public void testGetTotalBalance() throws InvalidInputException {
        accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        accountService.createCheckingAccount("Jane Smith", new BigDecimal("500.00"), false);
        
        assertEquals(new BigDecimal("1500.00"), accountService.getTotalBalance());
    }
    
    @Test
    public void testCloseAccount() throws InvalidInputException, AccountNotFoundException {
        Account account = accountService.createSavingsAccount("John Doe", new BigDecimal("1000.00"));
        String accountId = account.getAccountId();
        
        assertTrue(accountService.accountExists(accountId));
        accountService.closeAccount(accountId);
        assertFalse(accountService.accountExists(accountId));
    }
}
