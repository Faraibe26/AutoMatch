package com.banking;

import com.banking.services.AccountService;
import com.banking.services.CustomerService;
import com.banking.services.TransactionService;
import com.banking.utils.ConsoleUtil;

import java.util.Scanner;

public class BankingApp {
    private static final AccountService accountService = new AccountService();
    private static final CustomerService customerService = new CustomerService();
    private static final TransactionService transactionService = new TransactionService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            ConsoleUtil.displayMainMenu();
            int choice = ConsoleUtil.getUserInput("Choose an option: ");

            switch (choice) {
                case 1:
                    // Create a new account
                    accountService.createAccount();
                    break;
                case 2:
                    // Deposit money
                    accountService.deposit();
                    break;
                case 3:
                    // Withdraw money
                    accountService.withdraw();
                    break;
                case 4:
                    // Transfer money
                    accountService.transfer();
                    break;
                case 5:
                    // View transaction history
                    transactionService.viewTransactionHistory();
                    break;
                case 6:
                    // Exit the application
                    running = false;
                    ConsoleUtil.displayMessage("Thank you for using the Banking Management System!");
                    break;
                default:
                    ConsoleUtil.displayMessage("Invalid option. Please try again.");
            }
        }
    }
}