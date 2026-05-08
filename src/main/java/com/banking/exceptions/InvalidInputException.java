package com.banking.exceptions;

/**
 * Exception thrown when invalid input or invalid amount is provided to the banking system.
 * This exception covers cases like negative amounts, null values, or invalid data formats.
 */
public class InvalidInputException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private String fieldName;
    private String invalidValue;
    
    /**
     * Constructs an InvalidInputException with a detailed message.
     *
     * @param message the detail message
     */
    public InvalidInputException(String message) {
        super(message);
    }
    
    /**
     * Constructs an InvalidInputException with field and value information.
     *
     * @param fieldName the name of the field with invalid input
     * @param invalidValue the invalid value provided
     * @param reason the reason why the value is invalid
     */
    public InvalidInputException(String fieldName, String invalidValue, String reason) {
        super(String.format("Invalid input for field '%s': '%s'. Reason: %s", 
                fieldName, invalidValue, reason));
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getInvalidValue() {
        return invalidValue;
    }
}
