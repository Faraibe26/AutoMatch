# 🎯 Quick Reference Card

## Git Push Commands

### Option 1: HTTPS (Easiest)
```bash
cd /Users/faraibekhan/BankAccount/BankAccount
git remote add origin https://github.com/YOUR_USERNAME/banking-management-system.git
git push -u origin main
```

### Option 2: SSH (More Secure)
```bash
git remote add origin git@github.com:YOUR_USERNAME/banking-management-system.git
git push -u origin main
```

## Project Structure

```
banking-management-system/
├── src/main/java/com/banking/
│   ├── BankingApp.java (Main CLI Application)
│   ├── models/
│   │   ├── Account.java (Abstract Base)
│   │   ├── SavingsAccount.java
│   │   ├── CheckingAccount.java
│   │   ├── Transaction.java
│   │   └── Customer.java
│   ├── services/
│   │   ├── AccountService.java
│   │   ├── CustomerService.java
│   │   ├── TransactionService.java
│   │   └── FileManager.java
│   ├── exceptions/
│   │   ├── InsufficientFundsException.java
│   │   ├── AccountNotFoundException.java
│   │   └── InvalidInputException.java
│   └── utils/
│       ├── ConsoleUtil.java
│       └── ValidationUtil.java
├── README.md
├── pom.xml
└── .gitignore
```

## Key Classes & Responsibilities

| Class | Purpose |
|-------|---------|
| `Account` | Abstract base class for all accounts |
| `SavingsAccount` | Account with 6 withdrawal limit |
| `CheckingAccount` | Account with unlimited withdrawals |
| `Transaction` | Immutable transaction record |
| `AccountService` | Account business logic |
| `CustomerService` | Customer management |
| `TransactionService` | Transaction queries |
| `FileManager` | File persistence |
| `ConsoleUtil` | CLI utilities |
| `ValidationUtil` | Input validation |

## OOP Principles Used

✅ **Encapsulation** - Private fields with controlled access  
✅ **Inheritance** - Subclasses extend Account  
✅ **Polymorphism** - Different withdrawal behaviors  
✅ **Abstraction** - Abstract Account class  

## Features Implemented

✅ Account creation (Savings/Checking)  
✅ Deposit operations  
✅ Withdrawal with validation  
✅ Transfer between accounts  
✅ Transaction history tracking  
✅ File persistence (JSON/CSV)  
✅ Input validation  
✅ Exception handling  
✅ CLI interface  
✅ Unit tests  

## Usage Examples

### Create a Savings Account
```
Menu: 1
Name: John Smith
Deposit: 5000
Type: 1 (Savings)
```

### Deposit Money
```
Menu: 2
Account ID: ACC-1715167200000-1001
Amount: 1000
```

### Withdraw Money
```
Menu: 3
Account ID: ACC-1715167200000-1001
Amount: 500
```

### Transfer Money
```
Menu: 4
From Account: ACC-1715167200000-1001
To Account: ACC-1715167200000-1002
Amount: 1000
```

## Exception Handling

| Exception | When Thrown |
|-----------|-------------|
| `InsufficientFundsException` | Withdrawal > balance |
| `AccountNotFoundException` | Invalid account ID |
| `InvalidInputException` | Invalid input (negative, empty) |

## Testing Commands

```bash
# Compile
javac -d build/classes -encoding UTF-8 \
  $(find src/main/java -name "*.java")

# Run application
java -cp build/classes com.banking.BankingApp

# Run tests (if JUnit available)
java -cp build/classes:build/test-classes \
  org.junit.runner.JUnitCore \
  com.banking.models.AccountTest
```

## Git Workflow

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes
vim src/main/java/com/banking/models/Account.java

# Stage changes
git add .

# Commit
git commit -m "feat: add new feature description"

# Push
git push origin feature/new-feature

# Create Pull Request on GitHub
```

## Common Git Commands

| Command | Purpose |
|---------|---------|
| `git status` | Check repo status |
| `git log` | View commit history |
| `git diff` | See what changed |
| `git add .` | Stage all changes |
| `git commit -m "msg"` | Create commit |
| `git push` | Push to remote |
| `git pull` | Fetch & merge changes |
| `git branch -a` | List all branches |
| `git checkout -b name` | Create & switch branch |

## Interview Talking Points

**"This project demonstrates..."**

- ✅ Complete OOP implementation
- ✅ Abstract classes & inheritance
- ✅ Polymorphic behavior
- ✅ Encapsulation principles
- ✅ Service layer architecture
- ✅ Custom exception hierarchy
- ✅ File I/O operations
- ✅ Unit testing practices
- ✅ Clean code principles
- ✅ SOLID design patterns

## Documentation Files

| File | Content |
|------|---------|
| `README.md` | Main documentation |
| `ARCHITECTURE.md` | Design patterns & architecture |
| `GITHUB_DEPLOYMENT.md` | GitHub setup guide |
| `CONTRIBUTING.md` | Contribution guidelines |
| `USAGE_GUIDE.md` | How to use features |
| `GETTING_STARTED.md` | Setup instructions |
| `PUSH_TO_GITHUB.md` | Push checklist |

## GitHub Links

- Repository: `https://github.com/YOUR_USERNAME/banking-management-system`
- Issues: `https://github.com/YOUR_USERNAME/banking-management-system/issues`
- Pull Requests: `https://github.com/YOUR_USERNAME/banking-management-system/pulls`
- Actions: `https://github.com/YOUR_USERNAME/banking-management-system/actions`

## Portfolio Tips

🎯 **Showcase:**
- Link from portfolio website
- Pin to GitHub profile
- Mention in resume
- Highlight in interviews

📊 **Metrics to highlight:**
- 12+ classes
- 1500+ lines
- 20+ test cases
- 7 documentation files
- Full OOP implementation

🚀 **Future upgrades:**
- Spring Boot REST API
- PostgreSQL integration
- Docker containerization
- Frontend UI
- AWS deployment

---

**Ready to push?** Run these commands:

```bash
cd /Users/faraibekhan/BankAccount/BankAccount
git remote add origin https://github.com/YOUR_USERNAME/banking-management-system.git
git push -u origin main
```

**Replace `YOUR_USERNAME` with your GitHub username!** ✨
