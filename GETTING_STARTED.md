# Getting Started with Banking Management System

## 🎯 Welcome!

Welcome to the Banking Management System - a professional, production-grade banking application built with Java. This guide will help you get started quickly.

## ⚡ 30-Second Quick Start

```bash
cd /Users/faraibekhan/BankAccount/BankAccount
./run.sh
```

Then follow the on-screen menu to create accounts and start banking!

## 📖 Reading Guide

Choose based on your interest:

### I want to USE the application
→ Read [USAGE_GUIDE.md](USAGE_GUIDE.md)

### I want to UNDERSTAND the code
→ Read [ARCHITECTURE.md](ARCHITECTURE.md)

### I want to know PROJECT STATUS
→ Read [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

### I want COMPLETE OVERVIEW
→ Read [README.md](README.md)

### I need NAVIGATION HELP
→ Read [INDEX.md](INDEX.md)

## 🚀 Three Ways to Run

### Method 1: Shell Script (Recommended)
```bash
chmod +x run.sh
./run.sh
```

### Method 2: Direct Java
```bash
java -cp build/classes com.banking.BankingApp
```

### Method 3: Maven
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.banking.BankingApp"
```

## 📋 What You Can Do

✅ Create bank accounts (Savings or Checking)
✅ Deposit money
✅ Withdraw money
✅ Transfer between accounts
✅ View account details
✅ See transaction history
✅ Export data to CSV
✅ Save data to JSON

## 🎮 Sample Session

1. Select option 1: Create Account
2. Name: "John Doe"
3. Initial deposit: "5000"
4. Account type: "1" (Savings)
5. Select option 2: Deposit Money
6. Deposit: "1000"
7. Select option 6: View Transactions
8. See your transaction history!

## 📁 Project Structure

```
/Users/faraibekhan/BankAccount/BankAccount/
├── src/                    (Source code)
├── build/classes/          (Compiled code)
├── data/                   (Saved data)
├── README.md               (Start here)
├── USAGE_GUIDE.md         (How to use)
├── ARCHITECTURE.md        (How it works)
└── run.sh                 (Start script)
```

## ✨ Key Features

- **Savings Account**: 6 withdrawals/month, $100 minimum balance
- **Checking Account**: Unlimited withdrawals, optional overdraft protection
- **Colored Output**: Professional green/red/yellow messages
- **Data Persistence**: Automatically save to JSON, export to CSV
- **Exception Handling**: Robust error handling throughout

## 💾 Data Handling

### Automatic Save
Data is automatically saved to `data/accounts.json` when you select "Save and Exit"

### CSV Export
Export accounts and transactions to CSV files for use in Excel

### Auto-Load
Your data loads automatically when you restart the application

## 🧪 Testing the Features

### Test 1: Create Accounts
- Create a Savings Account with $5,000
- Create a Checking Account with $2,000
- Both should appear in "View All Accounts"

### Test 2: Transactions
- Deposit $1,000 into Savings Account
- Withdraw $500 from Savings Account
- Transfer $1,000 to Checking Account

### Test 3: View Data
- View transaction history (should show 4 transactions)
- Export to CSV
- View all accounts

## ⚙️ System Requirements

- ✅ Java 11 or higher (tested on Java 21)
- ✅ 500MB disk space (for project + data)
- ✅ Terminal/Command line
- ✅ Text editor (optional, for viewing code)

## 🔧 Troubleshooting

### "Command not found: run.sh"
```bash
chmod +x run.sh
./run.sh
```

### "No accounts found"
This is normal on first run. Create an account using option 1.

### "Data not saving"
Make sure to use option 9 (Save and Exit), not option 10.

### Application won't run
Try: `java -cp build/classes com.banking.BankingApp`

## 📚 Learning Path

1. **Day 1**: Use the application, understand features
2. **Day 2**: Read USAGE_GUIDE.md with examples
3. **Day 3**: Read ARCHITECTURE.md and understand design
4. **Day 4**: Review source code, study implementation
5. **Day 5**: Experiment and extend with new features

## 🎓 Learning Topics

You'll learn about:
- ✅ Object-Oriented Programming (OOP)
- ✅ Design Patterns
- ✅ Clean Code principles
- ✅ Exception handling
- ✅ Data persistence
- ✅ CLI design
- ✅ SOLID principles

## 🚀 Next Steps After Learning

1. **Extend the project**
   - Add interest calculation
   - Add account freezing
   - Add transaction limits

2. **Add a database**
   - Replace HashMap with MySQL
   - Add Spring Data JPA

3. **Create REST API**
   - Use Spring Boot
   - Add Swagger documentation

4. **Build a web UI**
   - Use React or Angular
   - Connect to REST API

## 📞 Need Help?

- **Using the app?** → Read USAGE_GUIDE.md
- **Understanding code?** → Read ARCHITECTURE.md
- **Project questions?** → Read README.md
- **Need navigation?** → Read INDEX.md

## 🎉 You're All Set!

You're ready to use the Banking Management System. Choose your reading guide above and get started!

### Quick Links
- [README.md](README.md) - Complete overview
- [USAGE_GUIDE.md](USAGE_GUIDE.md) - Step-by-step examples
- [ARCHITECTURE.md](ARCHITECTURE.md) - Design deep-dive
- [INDEX.md](INDEX.md) - Full navigation

---

**Happy Banking! 🏦**

Version: 1.0.0 | Last Updated: May 8, 2026 | Status: Production Ready ✅
