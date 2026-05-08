# Banking Management System

## Overview
The Banking Management System is a CLI-based application designed to manage banking operations such as account creation, deposits, withdrawals, and transaction history. The system is built using object-oriented programming principles and follows a clean software engineering structure.

## Features
- Create and manage bank accounts
- Deposit and withdraw funds
- Transfer money between accounts
- View transaction history
- Input validation and error handling

## Project Structure
```
banking-management-system
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── banking
│   │               ├── BankingApp.java
│   │               ├── models
│   │               │   ├── Account.java
│   │               │   ├── Customer.java
│   │               │   └── Transaction.java
│   │               ├── services
│   │               │   ├── AccountService.java
│   │               │   ├── CustomerService.java
│   │               │   └── TransactionService.java
│   │               ├── utils
│   │               │   ├── ValidationUtil.java
│   │               │   └── ConsoleUtil.java
│   │               └── exceptions
│   │                   ├── InsufficientFundsException.java
│   │                   ├── AccountNotFoundException.java
│   │                   └── InvalidInputException.java
│   └── test
│       └── java
│           └── com
│               └── banking
│                   ├── models
│                   │   └── AccountTest.java
│                   └── services
│                       └── AccountServiceTest.java
├── pom.xml
└── README.md
```

## Setup Instructions
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd banking-management-system
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn exec:java -Dexec.mainClass="com.banking.BankingApp"
   ```

## Usage Examples
- **Create an Account**: Follow the prompts to enter your details and create a new bank account.
- **Deposit Funds**: Select the deposit option, enter the account ID and amount to deposit.
- **Withdraw Funds**: Select the withdrawal option, enter the account ID and amount to withdraw.
- **Transfer Funds**: Choose the transfer option, provide the source and destination account IDs along with the amount.
- **View Transactions**: Select the option to view transaction history for a specific account.

## Future Upgrades
- Convert the application to a Spring Boot REST API for better scalability and integration with web applications.
- Implement a user authentication system for enhanced security.
- Add a graphical user interface (GUI) for improved user experience.
- Integrate with a database for persistent data storage.