package com.banking;

import com.banking.models.Account;
import com.banking.models.CheckingAccount;
import com.banking.models.SavingsAccount;
import com.banking.services.AccountService;
import com.banking.services.CustomerService;
import com.banking.services.FileManager;
import com.banking.services.TransactionService;
import com.banking.utils.ConsoleUtil;
import com.banking.exceptions.*;

import java.math.BigDecimal;

/**
 * Main application class for the Banking Management System.
 * Provides a CLI interface for users to interact with the banking system.
 * 
 * This class demonstrates the MVC/MVP architectural pattern with separation of concerns.
 */
public class BankingApp {
    
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final FileManager fileManager;
    private boolean isRunning;
    
    /**
     * Constructs the BankingApp and initializes all services.
     */
    public BankingApp() {
        this.accountService = new AccountService();
        this.customerService = new CustomerService();
        this.transactionService = new TransactionService();
        this.fileManager = new FileManager();
        this.isRunning = true;
    }
    
    /**
     * Starts the banking application and displays the main menu.
     */
    public void start() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Welcome to Banking Management System");
        ConsoleUtil.printInfo("A Professional Banking Application");
        ConsoleUtil.println();
        
        while (isRunning) {
            displayMainMenu();
        }
        
        ConsoleUtil.printSuccess("Thank you for using Banking Management System!");
        ConsoleUtil.closeScanner();
    }
    
    /**
     * Displays the main menu and handles user selection.
     */
    private void displayMainMenu() {
        ConsoleUtil.printSection("Main Menu");
        ConsoleUtil.println("1. Create Account");
        ConsoleUtil.println("2. Deposit Money");
        ConsoleUtil.println("3. Withdraw Money");
        ConsoleUtil.println("4. Transfer Money");
        ConsoleUtil.println("5. View Account Details");
        ConsoleUtil.println("6. View Transaction History");
        ConsoleUtil.println("7. View All Accounts");
        ConsoleUtil.println("8. Export Account Data");
        ConsoleUtil.println("9. Save Data");
        ConsoleUtil.println("10. Exit");
        ConsoleUtil.println();
        
        int choice = ConsoleUtil.readInt("Enter your choice (1-10): ");
        
        switch (choice) {
            case 1:
                handleCreateAccount();
                break;
            case 2:
                handleDeposit();
                break;
            case 3:
                handleWithdraw();
                break;
            case 4:
                handleTransfer();
                break;
            case 5:
                handleViewAccount();
                break;
            case 6:
                handleViewTransactions();
                break;
            case 7:
                handleViewAllAccounts();
                break;
            case 8:
                handleExportData();
                break;
            case 9:
                handleSaveData();
                break;
            case 10:
                isRunning = false;
                break;
            default:
                ConsoleUtil.printError("Invalid choice. Please try again.");
        }
        
        if (isRunning && choice >= 1 && choice <= 9) {
            ConsoleUtil.pause();
        }
    }
    
    /**
     * Handles account creation with user input.
     */
    private void handleCreateAccount() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Create New Account");
        
        String accountHolder = ConsoleUtil.readString("Enter account holder name: ");
        
        // Initial balance input with validation
        BigDecimal initialBalance = getValidBigDecimalInput("Enter initial deposit (minimum $0): ");
        
        ConsoleUtil.printSection("Select Account Type");
        ConsoleUtil.println("1. Savings Account (Limited 6 withdrawals/month, $100 minimum balance)");
        ConsoleUtil.println("2. Checking Account (Unlimited withdrawals)");
        
        int accountType = ConsoleUtil.readInt("Enter account type (1 or 2): ");
        
        try {
            Account account;
            
            if (accountType == 1) {
                account = accountService.createSavingsAccount(accountHolder, initialBalance);
                ConsoleUtil.printSuccess("Savings Account created successfully!");
            } else if (accountType == 2) {
                boolean overdraftProtection = ConsoleUtil.readString(
                        "Enable overdraft protection? (yes/no): ").equalsIgnoreCase("yes");
                account = accountService.createCheckingAccount(accountHolder, initialBalance, overdraftProtection);
                ConsoleUtil.printSuccess("Checking Account created successfully!");
            } else {
                ConsoleUtil.printError("Invalid account type selection.");
                return;
            }
            
            ConsoleUtil.println("\nAccount Details:");
            ConsoleUtil.println(account.toString());
            
        } catch (InvalidInputException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles deposit operations.
     */
    private void handleDeposit() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Deposit Money");
        
        String accountId = ConsoleUtil.readString("Enter account ID: ");
        BigDecimal amount = getValidBigDecimalInput("Enter deposit amount: ");
        
        try {
            accountService.deposit(accountId, amount);
            Account account = accountService.getAccount(accountId);
            
            ConsoleUtil.printSuccess(String.format("Successfully deposited $%s to account %s", amount, accountId));
            ConsoleUtil.printInfo("New balance: $" + account.getBalance());
            
        } catch (AccountNotFoundException e) {
            ConsoleUtil.printError(e.getMessage());
        } catch (InvalidInputException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles withdrawal operations.
     */
    private void handleWithdraw() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Withdraw Money");
        
        String accountId = ConsoleUtil.readString("Enter account ID: ");
        BigDecimal amount = getValidBigDecimalInput("Enter withdrawal amount: ");
        
        try {
            accountService.withdraw(accountId, amount);
            Account account = accountService.getAccount(accountId);
            
            ConsoleUtil.printSuccess(String.format("Successfully withdrew $%s from account %s", amount, accountId));
            ConsoleUtil.printInfo("New balance: $" + account.getBalance());
            
            // Display specific account type info
            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                ConsoleUtil.printInfo("Remaining monthly withdrawals: " + savingsAccount.getRemainingMonthlyWithdrawals());
            }
            
        } catch (AccountNotFoundException e) {
            ConsoleUtil.printError(e.getMessage());
        } catch (InvalidInputException e) {
            ConsoleUtil.printError(e.getMessage());
        } catch (InsufficientFundsException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles money transfer between accounts.
     */
    private void handleTransfer() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Transfer Money Between Accounts");
        
        String fromAccountId = ConsoleUtil.readString("Enter sender account ID: ");
        String toAccountId = ConsoleUtil.readString("Enter recipient account ID: ");
        BigDecimal amount = getValidBigDecimalInput("Enter transfer amount: ");
        
        try {
            accountService.transfer(fromAccountId, toAccountId, amount);
            
            Account fromAccount = accountService.getAccount(fromAccountId);
            Account toAccount = accountService.getAccount(toAccountId);
            
            ConsoleUtil.printSuccess(String.format("Successfully transferred $%s from %s to %s", 
                    amount, fromAccountId, toAccountId));
            ConsoleUtil.printInfo(String.format("Sender balance: $%s", fromAccount.getBalance()));
            ConsoleUtil.printInfo(String.format("Recipient balance: $%s", toAccount.getBalance()));
            
        } catch (AccountNotFoundException e) {
            ConsoleUtil.printError(e.getMessage());
        } catch (InvalidInputException e) {
            ConsoleUtil.printError(e.getMessage());
        } catch (InsufficientFundsException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles viewing account details.
     */
    private void handleViewAccount() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("View Account Details");
        
        String accountId = ConsoleUtil.readString("Enter account ID: ");
        
        try {
            Account account = accountService.getAccount(accountId);
            ConsoleUtil.println();
            ConsoleUtil.println(account.toString());
            
        } catch (AccountNotFoundException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles viewing transaction history.
     */
    private void handleViewTransactions() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("View Transaction History");
        
        String accountId = ConsoleUtil.readString("Enter account ID: ");
        
        try {
            Account account = accountService.getAccount(accountId);
            transactionService.printTransactionHistory(account);
            
            // Print summary statistics
            ConsoleUtil.println();
            ConsoleUtil.printSection("Transaction Summary");
            ConsoleUtil.printInfo("Total Deposits: $" + transactionService.getTotalDeposits(account));
            ConsoleUtil.printInfo("Total Withdrawals: $" + transactionService.getTotalWithdrawals(account));
            
        } catch (AccountNotFoundException e) {
            ConsoleUtil.printError(e.getMessage());
        }
    }
    
    /**
     * Handles viewing all accounts in the system.
     */
    private void handleViewAllAccounts() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("All Accounts in System");
        
        if (accountService.getAccountCount() == 0) {
            ConsoleUtil.printWarning("No accounts found in the system.");
            return;
        }
        
        ConsoleUtil.println(String.format("Total Accounts: %d", accountService.getAccountCount()));
        ConsoleUtil.println(String.format("Total Balance Across All Accounts: $%s", accountService.getTotalBalance()));
        ConsoleUtil.printDivider();
        
        accountService.getAllAccounts().values().forEach(account -> {
            ConsoleUtil.println(account.toString());
            ConsoleUtil.printDivider();
        });
    }
    
    /**
     * Handles exporting account data to CSV files.
     */
    private void handleExportData() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Export Data");
        
        if (accountService.getAccountCount() == 0) {
            ConsoleUtil.printWarning("No accounts to export.");
            return;
        }
        
        ConsoleUtil.println("1. Export all accounts summary");
        ConsoleUtil.println("2. Export specific account transactions");
        
        int choice = ConsoleUtil.readInt("Enter your choice: ");
        
        if (choice == 1) {
            String filename = ConsoleUtil.readString("Enter filename (e.g., accounts_report.csv): ");
            if (fileManager.exportAccountsToCSV(accountService.getAllAccounts(), filename)) {
                ConsoleUtil.printSuccess("Accounts exported to data/" + filename);
            } else {
                ConsoleUtil.printError("Failed to export accounts.");
            }
        } else if (choice == 2) {
            String accountId = ConsoleUtil.readString("Enter account ID: ");
            try {
                Account account = accountService.getAccount(accountId);
                String filename = ConsoleUtil.readString("Enter filename (e.g., transactions.csv): ");
                if (fileManager.exportTransactionsToCSV(account, filename)) {
                    ConsoleUtil.printSuccess("Transactions exported to data/" + filename);
                } else {
                    ConsoleUtil.printError("Failed to export transactions.");
                }
            } catch (AccountNotFoundException e) {
                ConsoleUtil.printError(e.getMessage());
            }
        } else {
            ConsoleUtil.printError("Invalid choice.");
        }
    }
    
    /**
     * Handles saving application data to persistent storage.
     */
    private void handleSaveData() {
        ConsoleUtil.clearScreen();
        ConsoleUtil.printHeader("Save Data");
        
        if (fileManager.saveAccounts(accountService.getAllAccounts())) {
            ConsoleUtil.printSuccess("Application data saved successfully!");
            ConsoleUtil.printInfo("Data saved to data/accounts.json");
        } else {
            ConsoleUtil.printError("Failed to save data.");
        }
    }
    
    /**
     * Helper method to get a valid BigDecimal input from the user.
     *
     * @param prompt the prompt to display
     * @return a valid BigDecimal amount
     */
    private BigDecimal getValidBigDecimalInput(String prompt) {
        while (true) {
            try {
                String input = ConsoleUtil.readString(prompt);
                BigDecimal amount = new BigDecimal(input);
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    ConsoleUtil.printError("Amount cannot be negative.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                ConsoleUtil.printError("Invalid amount format. Please enter a valid number.");
            }
        }
    }
    
    /**
     * Main method to start the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        BankingApp app = new BankingApp();
        app.start();
    }
}
