# Banking Management System - Usage Guide

## Quick Start

### Running the Application

#### Method 1: Using the Shell Script
```bash
chmod +x run.sh
./run.sh
```

#### Method 2: Direct Java Command
```bash
cd /Users/faraibekhan/BankAccount/BankAccount
java -cp build/classes com.banking.BankingApp
```

#### Method 3: Using Maven (if installed)
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.banking.BankingApp"
```

---

## Sample Workflow

This guide walks through a typical session with the Banking Management System.

### Session Overview

We'll demonstrate:
1. Creating two accounts (Savings and Checking)
2. Making deposits and withdrawals
3. Transferring money between accounts
4. Viewing transaction history
5. Exporting data

---

## Detailed Operations

### 1. Create a Savings Account

**Input:**
```
Menu Choice: 1
Account Holder Name: Alice Johnson
Initial Deposit: 5000
Account Type: 1 (Savings Account)
```

**Output:**
```
============================================================
  Create New Account
============================================================

Enter account holder name: Alice Johnson
Enter initial deposit ($0 or more): $5000
Select Account Type:
1. Savings Account (Limited 6 withdrawals/month, $100 minimum balance)
2. Checking Account (Unlimited withdrawals)
Enter account type (1 or 2): 1

✓ Savings Account created successfully!

Account ID: ACC-1715167200000-1001
Account Holder: Alice Johnson
Account Type: Savings Account
Balance: $5000.00
Transactions: 1
Account Type Benefits:
  - Monthly Withdrawal Limit: 6
  - Remaining Withdrawals: 6
  - Minimum Balance Required: $100.00

Press Enter to continue...
```

**Account ID to Remember**: `ACC-1715167200000-1001`

---

### 2. Create a Checking Account

**Input:**
```
Menu Choice: 1
Account Holder Name: Bob Smith
Initial Deposit: 2000
Account Type: 2 (Checking Account)
Enable Overdraft Protection: y
```

**Output:**
```
============================================================
  Create New Account
============================================================

Enter account holder name: Bob Smith
Enter initial deposit ($0 or more): $2000
Select Account Type:
1. Savings Account (Limited 6 withdrawals/month, $100 minimum balance)
2. Checking Account (Unlimited withdrawals)
Enter account type (1 or 2): 2
Enable overdraft protection? (y/n): y

✓ Checking Account created successfully!

Account ID: ACC-1715167200000-1002
Account Holder: Bob Smith
Account Type: Checking Account
Balance: $2000.00
Transactions: 1
Account Type Benefits:
  - Unlimited Withdrawals
  - Overdraft Protection: Enabled
  - Overdraft Fee: $35.00

Press Enter to continue...
```

**Account ID to Remember**: `ACC-1715167200000-1002`

---

### 3. Deposit Money

**Input:**
```
Menu Choice: 2
Account ID: ACC-1715167200000-1001
Deposit Amount: 1500
```

**Output:**
```
============================================================
  Deposit Money
============================================================

Enter account ID: ACC-1715167200000-1001
Enter deposit amount: $1500

✓ Successfully deposited $1500.00 to account ACC-1715167200000-1001
ℹ New balance: $6500.00

Press Enter to continue...
```

---

### 4. Withdraw Money

**Input:**
```
Menu Choice: 3
Account ID: ACC-1715167200000-1001
Withdrawal Amount: 500
```

**Output:**
```
============================================================
  Withdraw Money
============================================================

Enter account ID: ACC-1715167200000-1001
Enter withdrawal amount: $500

✓ Successfully withdrew $500.00 from account ACC-1715167200000-1001
ℹ New balance: $6000.00
ℹ Remaining monthly withdrawals: 5

Press Enter to continue...
```

---

### 5. Transfer Money Between Accounts

**Input:**
```
Menu Choice: 4
Sender Account ID: ACC-1715167200000-1001
Recipient Account ID: ACC-1715167200000-1002
Transfer Amount: 1000
```

**Output:**
```
============================================================
  Transfer Money
============================================================

Enter sender account ID: ACC-1715167200000-1001
Enter recipient account ID: ACC-1715167200000-1002
Enter transfer amount: $1000

✓ Successfully transferred $1000.00 from ACC-1715167200000-1001 to ACC-1715167200000-1002
ℹ Sender balance: $5000.00
ℹ Recipient balance: $3000.00

Press Enter to continue...
```

---

### 6. View Transaction History

**Input:**
```
Menu Choice: 6
Account ID: ACC-1715167200000-1001
```

**Output:**
```
============================================================
  View Transaction History
============================================================

Enter account ID: ACC-1715167200000-1001

--- Transaction History for Account ACC-1715167200000-1001 ---
Total Transactions: 4
--------------------------------------------------------------------------------
[TXN-1715167200123-1001] Deposit - $5000.00
Time: 2026-05-08 14:30:45
Description: Account opened with initial deposit
--------------------------------------------------------------------------------
[TXN-1715167201234-1002] Deposit - $1500.00
Time: 2026-05-08 14:32:10
Description: Deposit to account
--------------------------------------------------------------------------------
[TXN-1715167202345-1003] Withdrawal - $500.00
Time: 2026-05-08 14:35:22
Description: Withdrawal from account
--------------------------------------------------------------------------------
[TXN-1715167203456-1004] Transfer Out - $1000.00
Time: 2026-05-08 14:38:15
Description: Transfer to account ACC-1715167200000-1002
Related Account: ACC-1715167200000-1002
--------------------------------------------------------------------------------

Press Enter to continue...
```

---

### 7. View All Accounts

**Input:**
```
Menu Choice: 7
```

**Output:**
```
============================================================
  All Accounts in System
============================================================

Total Accounts: 2
Total System Balance: $8000.00
------------------------------------------------------------

[Account 1]
ID: ACC-1715167200000-1001
Holder: Alice Johnson
Type: Savings Account
Balance: $5000.00
Transactions: 4

[Account 2]
ID: ACC-1715167200000-1002
Holder: Bob Smith
Type: Checking Account
Balance: $3000.00
Transactions: 2

Press Enter to continue...
```

---

### 8. Export Account Data

**Input:**
```
Menu Choice: 8
Filename: bank_export_2026-05-08
```

**Output:**
```
============================================================
  Export Account Data
============================================================

Enter filename (without extension): bank_export_2026-05-08

✓ Accounts exported to bank_export_2026-05-08_accounts.csv

Press Enter to continue...
```

**Generated File Contents** (`data/bank_export_2026-05-08_accounts.csv`):
```csv
Account ID,Account Holder,Account Type,Balance,Transaction Count
"ACC-1715167200000-1001","Alice Johnson","Savings Account","5000.00",4
"ACC-1715167200000-1002","Bob Smith","Checking Account","3000.00",2
```

---

### 9. Save and Exit

**Input:**
```
Menu Choice: 9
```

**Output:**
```
============================================================
  Saving Data
============================================================

✓ Data saved successfully to data/accounts.json

============================================================
  Thank you for using Banking Management System!
============================================================
✓ Data saved successfully. Goodbye!
```

**Generated File Contents** (`data/accounts.json`):
```json
[
  {
    "accountId": "ACC-1715167200000-1001",
    "accountHolder": "Alice Johnson",
    "accountType": "Savings Account",
    "balance": "5000.00",
    "transactionCount": 4
  },
  {
    "accountId": "ACC-1715167200000-1002",
    "accountHolder": "Bob Smith",
    "accountType": "Checking Account",
    "balance": "3000.00",
    "transactionCount": 2
  }
]
```

---

## Error Handling Examples

### Example 1: Insufficient Funds

**Input:**
```
Menu Choice: 3
Account ID: ACC-1715167200000-1002
Withdrawal Amount: 5000
```

**Output:**
```
============================================================
  Withdraw Money
============================================================

Enter account ID: ACC-1715167200000-1002
Enter withdrawal amount: $5000

✗ Error: Insufficient funds in account ACC-1715167200000-1002. Requested: $5000.00, Available: $3000.00

Press Enter to continue...
```

---

### Example 2: Account Not Found

**Input:**
```
Menu Choice: 5
Account ID: ACC-INVALID-1001
```

**Output:**
```
============================================================
  View Account Details
============================================================

Enter account ID: ACC-INVALID-1001

✗ Error: Account with ID 'ACC-INVALID-1001' not found in the banking system.

Press Enter to continue...
```

---

### Example 3: Invalid Input (Negative Amount)

**Input:**
```
Menu Choice: 2
Account ID: ACC-1715167200000-1001
Deposit Amount: -1000
```

**Output:**
```
============================================================
  Deposit Money
============================================================

Enter account ID: ACC-1715167200000-1001
Enter deposit amount: $-1000
✗ Error: Invalid input for field 'deposit amount': '-1000.00'. Reason: Amount must be greater than zero

✗ Error: Invalid input for field 'deposit amount': '-1000.00'. Reason: Amount must be greater than zero

Press Enter to continue...
```

---

### Example 4: Savings Account Withdrawal Limit Exceeded

**Input:**
```
Menu Choice: 3
Account ID: ACC-1715167200000-1001
Withdrawal Amount: 100  # (repeat 7 times)
```

**Output (on 7th withdrawal):**
```
============================================================
  Withdraw Money
============================================================

Enter account ID: ACC-1715167200000-1001
Enter withdrawal amount: $100

✗ Error: Invalid input for field 'withdrawal': '6'. Reason: Monthly withdrawal limit of 6 exceeded

Press Enter to continue...
```

---

### Example 5: Savings Account Minimum Balance Violation

**Input:**
```
Menu Choice: 3
Account ID: ACC-1715167200000-1001
Withdrawal Amount: 5000  # Savings balance is $5000, minimum is $100
```

**Output:**
```
============================================================
  Withdraw Money
============================================================

Enter account ID: ACC-1715167200000-1001
Enter withdrawal amount: $5000

✗ Error: Invalid input for field 'withdrawal': '5000.00'. Reason: Cannot withdraw below minimum balance of $100.00

Press Enter to continue...
```

---

## Data Persistence

### File Location
Data is automatically saved to: `data/accounts.json`

### Loading Data
When the application restarts, it can load previously saved accounts using the `loadAccounts()` method in `FileManager`.

### CSV Export
Accounts can be exported to CSV for use in Excel or other tools.

---

## Account Types and Features

### Savings Account
- **Monthly Withdrawal Limit**: 6 withdrawals per month
- **Minimum Balance**: $100.00 must remain at all times
- **Ideal For**: Long-term savings, infrequent withdrawals
- **Restrictions**: Cannot withdraw below minimum balance

### Checking Account
- **Withdrawal Limit**: Unlimited
- **Minimum Balance**: None
- **Overdraft Protection**: Optional
- **Overdraft Fee**: $35.00 (if protection enabled)
- **Ideal For**: Frequent transactions, day-to-day banking

---

## Tips and Best Practices

1. **Keep Track of Account IDs**: Always save your account IDs for easy reference
2. **Monitor Withdrawal Limits**: For Savings accounts, keep track of remaining withdrawals
3. **Regular Exports**: Export data regularly for backup purposes
4. **Save Before Exiting**: Always choose option 9 to save data before exiting
5. **Check Balances**: View account details to check current balances before transactions

---

## Troubleshooting

### Issue: "Account not found" error
- **Solution**: Verify the account ID is correct. Use option 7 to view all accounts.

### Issue: "Insufficient funds" error
- **Solution**: Check your balance using option 5. Deposit more funds or reduce withdrawal amount.

### Issue: Data not saved
- **Solution**: Make sure to use option 9 (Save and Exit) instead of option 10 (Exit Without Save).

### Issue: Invalid amount error
- **Solution**: Enter positive numbers only. Negative amounts and zero are not allowed.

---

## Project Structure Reference

```
banking-management-system/
├── src/main/java/com/banking/
│   ├── BankingApp.java           (Main CLI application)
│   ├── models/
│   │   ├── Account.java          (Abstract base class)
│   │   ├── SavingsAccount.java    (Savings account)
│   │   ├── CheckingAccount.java   (Checking account)
│   │   ├── Customer.java          (Customer profile)
│   │   └── Transaction.java       (Transaction record)
│   ├── services/
│   │   ├── AccountService.java    (Account operations)
│   │   ├── CustomerService.java   (Customer operations)
│   │   ├── TransactionService.java (Transaction queries)
│   │   └── FileManager.java       (Data persistence)
│   ├── exceptions/
│   │   ├── InsufficientFundsException.java
│   │   ├── AccountNotFoundException.java
│   │   └── InvalidInputException.java
│   └── utils/
│       ├── ConsoleUtil.java       (CLI utilities)
│       └── ValidationUtil.java    (Input validation)
├── data/                          (Automatically created)
│   ├── accounts.json              (Saved accounts)
│   └── *.csv                      (Exported data)
└── build/classes/                 (Compiled classes)
```

---

## Contact & Support

For issues or suggestions, please refer to the main README.md file or contact the development team.

**Last Updated**: May 8, 2026  
**Version**: 1.0.0
