// This utility class provides methods for displaying messages to the console and reading user input.

package com.banking.utils;

import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static String readInput(String prompt) {
        printMessage(prompt);
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }
}