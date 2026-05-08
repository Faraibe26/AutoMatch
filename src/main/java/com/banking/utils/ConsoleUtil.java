package com.banking.utils;

import java.util.Scanner;

/**
 * Utility class for console input/output operations.
 * Provides methods for displaying menus, prompts, and messages to the user.
 * 
 * This class demonstrates the Single Responsibility Principle (SRP).
 */
public class ConsoleUtil {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    // ANSI color codes for console output
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    
    // Private constructor to prevent instantiation
    private ConsoleUtil() {
    }
    
    /**
     * Prints a message with a newline.
     *
     * @param message the message to print
     */
    public static void println(String message) {
        System.out.println(message);
    }
    
    /**
     * Prints an empty line.
     */
    public static void println() {
        System.out.println();
    }
    
    /**
     * Prints a message without a newline.
     *
     * @param message the message to print
     */
    public static void print(String message) {
        System.out.print(message);
    }
    
    /**
     * Prints a success message in green.
     *
     * @param message the message to print
     */
    public static void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }
    
    /**
     * Prints an error message in red.
     *
     * @param message the message to print
     */
    public static void printError(String message) {
        System.out.println(RED + "✗ Error: " + message + RESET);
    }
    
    /**
     * Prints a warning message in yellow.
     *
     * @param message the message to print
     */
    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠ Warning: " + message + RESET);
    }
    
    /**
     * Prints an info message in blue.
     *
     * @param message the message to print
     */
    public static void printInfo(String message) {
        System.out.println(BLUE + "ℹ " + message + RESET);
    }
    
    /**
     * Prints a header message in cyan.
     *
     * @param header the header message to print
     */
    public static void printHeader(String header) {
        System.out.println(CYAN + "\n" + "=".repeat(60) + RESET);
        System.out.println(CYAN + "  " + header + RESET);
        System.out.println(CYAN + "=".repeat(60) + RESET);
    }
    
    /**
     * Prints a section header in cyan.
     *
     * @param section the section header to print
     */
    public static void printSection(String section) {
        System.out.println(CYAN + "\n--- " + section + " ---" + RESET);
    }
    
    /**
     * Reads a line of input from the user.
     *
     * @param prompt the prompt to display
     * @return the user's input
     */
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Reads an integer from the user with validation.
     *
     * @param prompt the prompt to display
     * @return the user's input as an integer
     */
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                printError("Please enter a valid integer");
            }
        }
    }
    
    /**
     * Reads a double from the user with validation.
     *
     * @param prompt the prompt to display
     * @return the user's input as a double
     */
    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    printError("Amount cannot be negative");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                printError("Please enter a valid amount");
            }
        }
    }
    
    /**
     * Prints a divider line.
     */
    public static void printDivider() {
        System.out.println("-".repeat(60));
    }
    
    /**
     * Clears the console screen.
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pauses the application and waits for user to press Enter.
     */
    public static void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Closes the scanner resource.
     */
    public static void closeScanner() {
        scanner.close();
    }
}
