package com.banking.services;

import com.banking.models.Customer;
import com.banking.models.Account;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);
        customers.add(customer);
        return customer;
    }

    public void linkAccountToCustomer(Customer customer, Account account) {
        customer.addAccount(account);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}