package com.banking.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer of the banking system.
 * A customer can have multiple accounts.
 * 
 * Encapsulation: All fields are private with controlled access.
 */
public class Customer {
    
    private final String customerId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDate joinDate;
    private final List<Account> accounts;
    private static int customerCounter = 100;
    
    /**
     * Constructs a Customer with personal information.
     *
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     * @param email the customer's email address
     */
    public Customer(String firstName, String lastName, String email) {
        this.customerId = generateCustomerId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.joinDate = LocalDate.now();
        this.accounts = new ArrayList<>();
    }
    
    /**
     * Generates a unique customer ID.
     *
     * @return a unique customer ID
     */
    private synchronized static String generateCustomerId() {
        return "CUST-" + (++customerCounter);
    }
    
    /**
     * Adds an account to the customer's account list.
     *
     * @param account the account to add
     */
    public void addAccount(Account account) {
        if (account != null && !accounts.contains(account)) {
            accounts.add(account);
        }
    }
    
    /**
     * Removes an account from the customer's account list.
     *
     * @param account the account to remove
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
    }
    
    /**
     * Gets all accounts owned by this customer.
     *
     * @return an unmodifiable list of accounts
     */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }
    
    /**
     * Gets the number of accounts owned by this customer.
     *
     * @return the number of accounts
     */
    public int getAccountCount() {
        return accounts.size();
    }
    
    // Getters
    public String getCustomerId() {
        return customerId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Customer ID: %s%nName: %s%nEmail: %s%nJoin Date: %s%nNumber of Accounts: %d",
            customerId, getFullName(), email, joinDate, accounts.size()
        );
    }
}
