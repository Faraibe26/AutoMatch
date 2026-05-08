package com.banking.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a financial transaction in the banking system.
 * Each transaction is immutable after creation to ensure data integrity.
 * 
 * Encapsulation: All fields are private with public getters only.
 */
public class Transaction {
    
    public enum TransactionType {
        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        TRANSFER_OUT("Transfer Out"),
        TRANSFER_IN("Transfer In");
        
        private final String displayName;
        
        TransactionType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final String transactionId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String description;
    private final String relatedAccountId; // For transfers
    
    private static int transactionCounter = 1000;
    
    /**
     * Constructs a Transaction with all required details.
     *
     * @param type the type of transaction
     * @param amount the amount involved in the transaction
     * @param description a description of the transaction
     */
    public Transaction(TransactionType type, BigDecimal amount, String description) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.relatedAccountId = null;
    }
    
    /**
     * Constructs a Transaction with related account information (for transfers).
     *
     * @param type the type of transaction
     * @param amount the amount involved in the transaction
     * @param description a description of the transaction
     * @param relatedAccountId the ID of the related account (for transfers)
     */
    public Transaction(TransactionType type, BigDecimal amount, String description, String relatedAccountId) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.relatedAccountId = relatedAccountId;
    }
    
    /**
     * Generates a unique transaction ID.
     *
     * @return a unique transaction ID
     */
    private synchronized static String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + (++transactionCounter);
    }
    
    // Getters (Encapsulation - no setters for immutability)
    public String getTransactionId() {
        return transactionId;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getRelatedAccountId() {
        return relatedAccountId;
    }
    
    /**
     * Returns a formatted string representation of the transaction.
     *
     * @return formatted transaction details
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] %s - $%s%n", 
                transactionId, type.getDisplayName(), amount));
        sb.append(String.format("Time: %s%n", timestamp.format(formatter)));
        sb.append(String.format("Description: %s", description));
        if (relatedAccountId != null) {
            sb.append(String.format("%nRelated Account: %s", relatedAccountId));
        }
        return sb.toString();
    }
}
