package com.banking.utils;

import java.math.BigDecimal;
import com.banking.exceptions.InvalidInputException;

/**
 * Utility class for validating inputs in the banking system.
 * Contains static methods for various validation operations.
 * 
 * This class demonstrates the Single Responsibility Principle (SRP).
 */
public class ValidationUtil {
    
    // Private constructor to prevent instantiation
    private ValidationUtil() {
    }
    
    /**
     * Validates that a string is not null or empty.
     *
     * @param value the string to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the string is null or empty
     */
    public static void validateNotEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(
                fieldName,
                value != null ? value : "null",
                "Field cannot be empty"
            );
        }
    }
    
    /**
     * Validates that a BigDecimal amount is positive.
     *
     * @param amount the amount to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the amount is null, negative, or zero
     */
    public static void validatePositiveAmount(BigDecimal amount, String fieldName) throws InvalidInputException {
        if (amount == null) {
            throw new InvalidInputException(fieldName, "null", "Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidInputException(fieldName, amount.toString(), "Amount must be greater than zero");
        }
    }
    
    /**
     * Validates that a BigDecimal amount is non-negative.
     *
     * @param amount the amount to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the amount is null or negative
     */
    public static void validateNonNegativeAmount(BigDecimal amount, String fieldName) throws InvalidInputException {
        if (amount == null) {
            throw new InvalidInputException(fieldName, "null", "Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidInputException(fieldName, amount.toString(), "Amount cannot be negative");
        }
    }
    
    /**
     * Validates that an email is in a valid format.
     *
     * @param email the email to validate
     * @throws InvalidInputException if the email is invalid
     */
    public static void validateEmail(String email) throws InvalidInputException {
        validateNotEmpty(email, "email");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidInputException("email", email, "Invalid email format");
        }
    }
    
    /**
     * Validates that a value is not null.
     *
     * @param value the value to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the value is null
     */
    public static void validateNotNull(Object value, String fieldName) throws InvalidInputException {
        if (value == null) {
            throw new InvalidInputException(fieldName, "null", "Value cannot be null");
        }
    }
    
    /**
     * Validates that a string matches a specific pattern.
     *
     * @param value the value to validate
     * @param pattern the regex pattern
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if the value doesn't match the pattern
     */
    public static void validatePattern(String value, String pattern, String fieldName) throws InvalidInputException {
        validateNotEmpty(value, fieldName);
        if (!value.matches(pattern)) {
            throw new InvalidInputException(fieldName, value, "Invalid format");
        }
    }
}
