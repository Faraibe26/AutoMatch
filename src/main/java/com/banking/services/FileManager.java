package com.banking.services;

import com.banking.models.Account;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

/**
 * Service class for managing file persistence operations.
 * Handles saving and loading account data using JSON format.
 * 
 * This class demonstrates file I/O operations and data serialization.
 */
public class FileManager {
    
    private static final String DATA_DIRECTORY = "data";
    private static final String ACCOUNTS_FILE = "accounts.json";
    
    /**
     * Constructs a FileManager and ensures the data directory exists.
     */
    public FileManager() {
        ensureDataDirectoryExists();
    }
    
    /**
     * Ensures the data directory exists, creating it if necessary.
     */
    private void ensureDataDirectoryExists() {
        try {
            Path dataPath = Paths.get(DATA_DIRECTORY);
            Files.createDirectories(dataPath);
        } catch (IOException e) {
            System.err.println("Warning: Could not create data directory. " + e.getMessage());
        }
    }
    
    /**
     * Escapes special characters in JSON strings.
     *
     * @param str the string to escape
     * @return the escaped string
     */
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * Saves all accounts to a JSON file.
     *
     * @param accounts the map of accounts to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveAccounts(Map<String, Account> accounts) {
        try {
            Path filePath = Paths.get(DATA_DIRECTORY, ACCOUNTS_FILE);
            
            StringBuilder json = new StringBuilder("[\n");
            int count = 0;
            int total = accounts.size();
            
            for (Account account : accounts.values()) {
                json.append("  {\n");
                json.append("    \"accountId\": \"").append(account.getAccountId()).append("\",\n");
                json.append("    \"accountHolder\": \"").append(escapeJson(account.getAccountHolder())).append("\",\n");
                json.append("    \"accountType\": \"").append(account.getAccountType()).append("\",\n");
                json.append("    \"balance\": \"").append(account.getBalance().toString()).append("\",\n");
                json.append("    \"transactionCount\": ").append(account.getTransactionHistory().size()).append("\n");
                json.append("  }");
                
                count++;
                if (count < total) {
                    json.append(",");
                }
                json.append("\n");
            }
            
            json.append("]");
            Files.writeString(filePath, json.toString());
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Loads accounts from the JSON file.
     *
     * @return a list of account data, or an empty list if file doesn't exist
     */
    public List<Map<String, Object>> loadAccounts() {
        try {
            Path filePath = Paths.get(DATA_DIRECTORY, ACCOUNTS_FILE);
            
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }
            
            String json = Files.readString(filePath);
            List<Map<String, Object>> accounts = new ArrayList<>();
            
            // Simple JSON parsing without external libraries
            json = json.replaceAll("[\\[\\]\\n\\r\\s]", "");
            String[] accountStrings = json.split("\\},\\{");
            
            for (String accountStr : accountStrings) {
                accountStr = accountStr.replaceAll("[{}]", "");
                Map<String, Object> accountMap = new LinkedHashMap<>();
                
                String[] pairs = accountStr.split(",");
                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].replaceAll("[\"\\s]", "");
                        String value = keyValue[1].replaceAll("[\"\\s]", "");
                        accountMap.put(key, value);
                    }
                }
                
                if (!accountMap.isEmpty()) {
                    accounts.add(accountMap);
                }
            }
            
            return accounts;
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Exports account details to a CSV file.
     *
     * @param accounts the map of accounts to export
     * @param filename the filename for the export
     * @return true if exported successfully, false otherwise
     */
    public boolean exportAccountsToCSV(Map<String, Account> accounts, String filename) {
        try {
            Path filePath = Paths.get(DATA_DIRECTORY, filename);
            
            StringBuilder csv = new StringBuilder();
            csv.append("Account ID,Account Holder,Account Type,Balance,Transaction Count\n");
            
            for (Account account : accounts.values()) {
                csv.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",%d\n",
                        account.getAccountId(),
                        account.getAccountHolder(),
                        account.getAccountType(),
                        account.getBalance(),
                        account.getTransactionHistory().size()));
            }
            
            Files.writeString(filePath, csv.toString());
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Exports transaction history for a specific account to a CSV file.
     *
     * @param account the account whose transactions to export
     * @param filename the filename for the export
     * @return true if exported successfully, false otherwise
     */
    public boolean exportTransactionsToCSV(Account account, String filename) {
        try {
            Path filePath = Paths.get(DATA_DIRECTORY, filename);
            
            StringBuilder csv = new StringBuilder();
            csv.append("Transaction ID,Type,Amount,Timestamp,Description,Related Account\n");
            
            for (com.banking.models.Transaction transaction : account.getTransactionHistory()) {
                csv.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        transaction.getTransactionId(),
                        transaction.getType().getDisplayName(),
                        transaction.getAmount(),
                        transaction.getTimestamp(),
                        transaction.getDescription(),
                        transaction.getRelatedAccountId() != null ? transaction.getRelatedAccountId() : "N/A"));
            }
            
            Files.writeString(filePath, csv.toString());
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting transactions to CSV: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Clears all saved account data.
     *
     * @return true if cleared successfully, false otherwise
     */
    public boolean clearData() {
        try {
            Path filePath = Paths.get(DATA_DIRECTORY, ACCOUNTS_FILE);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error clearing data: " + e.getMessage());
            return false;
        }
    }
}
