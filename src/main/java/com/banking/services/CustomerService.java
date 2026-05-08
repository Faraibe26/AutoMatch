package com.banking.services;

import com.banking.models.Customer;
import com.banking.models.Account;
import com.banking.exceptions.InvalidInputException;
import com.banking.utils.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing customer operations.
 * Implements business logic for customers using the Service Layer pattern.
 * 
 * Encapsulation: Hides customer management details from the UI layer.
 * Single Responsibility: Focuses on customer-related operations.
 */
public class CustomerService {
    
    private final Map<String, Customer> customers;
    
    /**
     * Constructs a CustomerService with an empty customer repository.
     */
    public CustomerService() {
        this.customers = new HashMap<>();
    }
    
    /**
     * Creates a new customer.
     *
     * @param firstName the customer's first name
     * @param lastName the customer's last name
     * @param email the customer's email address
     * @return the created customer
     * @throws InvalidInputException if inputs are invalid
     */
    public Customer createCustomer(String firstName, String lastName, String email) 
            throws InvalidInputException {
        
        ValidationUtil.validateNotEmpty(firstName, "First Name");
        ValidationUtil.validateNotEmpty(lastName, "Last Name");
        ValidationUtil.validateEmail(email);
        
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(customer.getCustomerId(), customer);
        
        return customer;
    }
    
    /**
     * Retrieves a customer by ID.
     *
     * @param customerId the customer ID
     * @return the customer or null if not found
     */
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    /**
     * Retrieves a customer by email.
     *
     * @param email the customer's email
     * @return the customer or null if not found
     */
    public Customer getCustomerByEmail(String email) {
        return customers.values().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Gets all customers.
     *
     * @return a map of all customers
     */
    public Map<String, Customer> getAllCustomers() {
        return new HashMap<>(customers);
    }
    
    /**
     * Adds an account to a customer.
     *
     * @param customerId the customer ID
     * @param account the account to add
     */
    public void addAccountToCustomer(String customerId, Account account) {
        Customer customer = customers.get(customerId);
        if (customer != null && account != null) {
            customer.addAccount(account);
        }
    }
    
    /**
     * Removes an account from a customer.
     *
     * @param customerId the customer ID
     * @param account the account to remove
     */
    public void removeAccountFromCustomer(String customerId, Account account) {
        Customer customer = customers.get(customerId);
        if (customer != null && account != null) {
            customer.removeAccount(account);
        }
    }
    
    /**
     * Deletes a customer.
     *
     * @param customerId the customer ID
     * @return true if deleted successfully, false if not found
     */
    public boolean deleteCustomer(String customerId) {
        return customers.remove(customerId) != null;
    }
    
    /**
     * Gets the total number of customers.
     *
     * @return the customer count
     */
    public int getCustomerCount() {
        return customers.size();
    }
    
    /**
     * Checks if a customer exists.
     *
     * @param customerId the customer ID
     * @return true if the customer exists, false otherwise
     */
    public boolean customerExists(String customerId) {
        return customers.containsKey(customerId);
    }
}
