# Banking Management System - Architecture & Design Documentation

## Table of Contents
1. [System Architecture](#system-architecture)
2. [Design Patterns](#design-patterns)
3. [OOP Principles](#oop-principles)
4. [Class Diagram](#class-diagram)
5. [Sequence Diagrams](#sequence-diagrams)
6. [Data Flow](#data-flow)
7. [Database Schema](#database-schema)
8. [Exception Hierarchy](#exception-hierarchy)
9. [Best Practices Implemented](#best-practices-implemented)

---

## System Architecture

### Layered Architecture

The Banking Management System follows a **three-layer architecture**:

```
┌─────────────────────────────────────────┐
│      Presentation Layer (CLI)           │
│     (BankingApp - User Interface)       │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Business Logic Layer               │
│  (Services - Business Rules & Logic)    │
│  ├── AccountService                     │
│  ├── CustomerService                    │
│  ├── TransactionService                 │
│  └── FileManager                        │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│      Data Layer (Models)                │
│  (POJOs - Data Representation)          │
│  ├── Account (Abstract)                 │
│  ├── SavingsAccount                     │
│  ├── CheckingAccount                    │
│  ├── Customer                           │
│  ├── Transaction                        │
│  └── File Storage (JSON/CSV)            │
└─────────────────────────────────────────┘
```

### Component Interaction

```
User Input (CLI)
    ↓
BankingApp (Main)
    ↓
├─→ AccountService (Business Logic)
│       ├─→ Account Models (Data)
│       └─→ Validators (Input Validation)
│
├─→ TransactionService (Query/Report)
│       └─→ Account Models (Data Access)
│
└─→ FileManager (Persistence)
        └─→ File System (JSON/CSV)
```

---

## Design Patterns

### 1. **Service Layer Pattern**
Separates business logic from presentation and data layers.

**Example:**
```java
// Presentation calls Service
accountService.deposit(accountId, amount);

// Service contains business logic
public void deposit(String accountId, BigDecimal amount) throws ... {
    // Validation, business rules, data operations
}
```

**Benefits:**
- Reusable business logic
- Easy to test
- Clear separation of concerns

---

### 2. **Model-View-Controller (MVC)**
Separation of concerns between data (Model), presentation (View), and logic (Controller).

```
Model (Account, Transaction)
    ↓
Controller (Services: AccountService, TransactionService)
    ↓
View (BankingApp - CLI)
```

---

### 3. **Repository Pattern**
Abstracts data access layer using collections (HashMap).

**Implementation:**
```java
public class AccountService {
    private final Map<String, Account> accounts = new HashMap<>();
    
    public Account getAccount(String accountId) { ... }
    public void addAccount(Account account) { ... }
}
```

**Benefits:**
- Easy to swap storage implementations (file, database, cloud)
- Centralized data access logic

---

### 4. **Factory Pattern**
Creates objects without exposing creation logic.

**Example:**
```java
// Service creates accounts
public Account createSavingsAccount(String holder, BigDecimal balance) {
    return new SavingsAccount(holder, balance);
}

public Account createCheckingAccount(String holder, BigDecimal balance, boolean overdraft) {
    return new CheckingAccount(holder, balance, overdraft);
}
```

---

### 5. **Template Method Pattern**
Defines algorithm skeleton in base class, subclasses implement specifics.

**Example:**
```java
// Abstract Account class defines withdrawal algorithm
public final void withdraw(BigDecimal amount) throws ... {
    // Common validation & logic
    // ...
    performWithdrawal(amount);  // Hook for subclasses
    // Common finalization
}

// SavingsAccount implements specific withdrawal rules
protected void performWithdrawal(BigDecimal amount) throws ... {
    // Savings-specific logic (monthly limit, minimum balance)
}

// CheckingAccount has no specific rules
protected void performWithdrawal(BigDecimal amount) {
    // No additional restrictions
}
```

---

### 6. **Immutable Object Pattern**
Transaction objects are immutable after creation.

```java
public class Transaction {
    private final String transactionId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String description;
    
    // No setters - immutable
    public String getTransactionId() { return transactionId; }
    // ...
}
```

**Benefits:**
- Thread-safe
- Prevents accidental modifications
- Easy to cache

---

## OOP Principles

### 1. **Encapsulation**
Hide internal implementation, expose only necessary interfaces.

**Example:**
```java
public class Account {
    private final String accountId;        // Private
    private BigDecimal balance;             // Private
    private final List<Transaction> transactionHistory;  // Private
    
    // Public getters only (read-only for collection)
    public String getAccountId() { return accountId; }
    public BigDecimal getBalance() { return balance; }
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
}
```

**Benefits:**
- Protects object state
- Allows internal refactoring without breaking clients
- Enforces business rules

---

### 2. **Inheritance**
Code reuse through hierarchical class structure.

```
Account (Abstract Base)
    ├── SavingsAccount
    └── CheckingAccount
```

**Example:**
```java
public abstract class Account {
    protected final String accountId;
    protected BigDecimal balance;
    
    public final void deposit(BigDecimal amount) { ... }
    public final void withdraw(BigDecimal amount) { ... }
    protected abstract void performWithdrawal(BigDecimal amount);
    public abstract String getAccountType();
}

public class SavingsAccount extends Account {
    protected void performWithdrawal(BigDecimal amount) { ... }
    public String getAccountType() { return "Savings Account"; }
}
```

**Benefits:**
- Code reuse
- Polymorphic behavior
- Hierarchical organization

---

### 3. **Polymorphism**
Objects of different types respond to same interface.

**Example:**
```java
Account account1 = new SavingsAccount("John", BigDecimal.valueOf(1000));
Account account2 = new CheckingAccount("Jane", BigDecimal.valueOf(500));

// Same interface, different behavior
account1.withdraw(amount);  // Uses SavingsAccount logic
account2.withdraw(amount);  // Uses CheckingAccount logic
```

**Benefits:**
- Flexible code
- Easy to extend with new types
- Loose coupling

---

### 4. **Abstraction**
Hide complexity, expose essential features.

**Example:**
```java
public abstract class Account {
    // Concrete methods (fully implemented)
    public final void deposit(BigDecimal amount) { ... }
    public final void withdraw(BigDecimal amount) { ... }
    
    // Abstract methods (to be implemented by subclasses)
    protected abstract void performWithdrawal(BigDecimal amount) throws InvalidInputException;
    public abstract String getAccountType();
}
```

**Benefits:**
- Reduces complexity
- Defines clear contracts
- Enables different implementations

---

## Class Diagram

```
┌──────────────────────────────────────┐
│        <<abstract>>                   │
│           Account                     │
├──────────────────────────────────────┤
│ - accountId: String                  │
│ - accountHolder: String              │
│ - balance: BigDecimal                │
│ - transactionHistory: List           │
├──────────────────────────────────────┤
│ + deposit(amount): void              │
│ + withdraw(amount): void             │
│ + transfer(amount, recipient): void  │
│ + getBalance(): BigDecimal           │
│ + getTransactionHistory(): List      │
│ # performWithdrawal(amount): void    │
│ + getAccountType(): String           │
└──────────────────────────────────────┘
         △              △
         │              │
         │              │
┌────────┴──────┐  ┌───┴─────────────┐
│ SavingsAccount│  │CheckingAccount  │
├───────────────┤  ├─────────────────┤
│ - monthlyWith-│  │ - overdraftProt-│
│   drawalCount │  │   ection: bool  │
├───────────────┤  ├─────────────────┤
│ + performWith-│  │ + performWith-  │
│   drawal()    │  │   drawal()      │
└───────────────┘  └─────────────────┘

┌──────────────────────────────────────┐
│         Transaction                  │
├──────────────────────────────────────┤
│ - transactionId: String              │
│ - type: TransactionType (enum)       │
│ - amount: BigDecimal                 │
│ - timestamp: LocalDateTime           │
│ - description: String                │
│ - relatedAccountId: String           │
├──────────────────────────────────────┤
│ + getType(): TransactionType         │
│ + getAmount(): BigDecimal            │
│ + getTimestamp(): LocalDateTime      │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│          Customer                    │
├──────────────────────────────────────┤
│ - customerId: String                 │
│ - firstName: String                  │
│ - lastName: String                   │
│ - email: String                      │
│ - joinDate: LocalDate                │
│ - accounts: List<Account>            │
├──────────────────────────────────────┤
│ + addAccount(account): void          │
│ + removeAccount(account): void       │
│ + getAccounts(): List                │
│ + getFullName(): String              │
└──────────────────────────────────────┘
```

---

## Sequence Diagrams

### Withdraw Operation

```
User    BankingApp    AccountService    Account    Validation
  │          │             │              │            │
  ├─withdraw─→           │              │            │
  │          ├─withdraw──→               │            │
  │          │             ├─validate───→            │
  │          │             │◄─validation ok─────────┤
  │          │             ├─performWithdrawal──────→
  │          │             │◄─OK─────────────────────┤
  │          │             ├─updateBalance──→       │
  │          │             ├─recordTransaction──→   │
  │          │◄─success────┤              │         │
  │◄─success─┤             │              │         │

User    BankingApp    AccountService    Account
  │          │             │              │
  ├─withdraw─→           │              │
  │          ├─withdraw──→               │
  │          │             ├─validate───→
  │          │             │◄─InsufficientFundsException
  │          │◄─exception──┤
  │◄─error───┤
```

### Transfer Operation

```
User    BankingApp    AccountService    Account1    Account2
  │          │             │              │           │
  ├─transfer─→           │              │           │
  │          ├─transfer──→               │           │
  │          │             ├─validate────→          │
  │          │             │◄─OK─────────┤          │
  │          │             ├─withdraw from Account1─→
  │          │             ├─deposit to Account2───→
  │          │             ├─record transfers────────┤
  │          │◄─success────┤              │         │
  │◄─success─┤
```

---

## Data Flow

### Account Creation Flow

```
User Input
    ↓
BankingApp.handleCreateAccount()
    ↓
ConsoleUtil.readString() ← User enters name
ConsoleUtil.readDouble() ← User enters initial deposit
ConsoleUtil.readInt() ← User selects account type
    ↓
ValidationUtil.validateNotEmpty(name)
ValidationUtil.validateNonNegativeAmount(deposit)
    ↓
AccountService.createSavingsAccount() OR
AccountService.createCheckingAccount()
    ↓
New Account Instance (SavingsAccount or CheckingAccount)
    ↓
Stored in HashMap<String, Account>
    ↓
Display confirmation to user
```

### Deposit Flow

```
User Input (Account ID, Amount)
    ↓
BankingApp.handleDeposit()
    ↓
ValidationUtil.validatePositiveAmount(amount)
    ↓
AccountService.deposit(accountId, amount)
    ↓
Account account = getAccount(accountId)
    ├─ account.deposit(amount)
    │   ├─ ValidationUtil.validatePositiveAmount()
    │   ├─ balance += amount
    │   └─ Add Transaction to history
    │
    └─ Return updated account
    ↓
Display new balance to user
```

### Transaction History Retrieval Flow

```
User Input (Account ID)
    ↓
BankingApp.handleViewTransactions()
    ↓
AccountService.getAccount(accountId)
    ├─ Retrieve from HashMap
    ├─ If not found → AccountNotFoundException
    └─ Return Account
    ↓
TransactionService.printTransactionHistory(account)
    ├─ Get account.getTransactionHistory()
    ├─ Format each transaction
    └─ Print to console
    ↓
Display formatted transaction history
```

### Data Persistence Flow

```
User selects "Save"
    ↓
BankingApp.saveAndExit()
    ↓
FileManager.saveAccounts(Map<String, Account>)
    ├─ Ensure data directory exists
    ├─ Convert accounts to JSON
    │   └─ Iterate through HashMap
    │       └─ Extract accountId, holder, type, balance, transactionCount
    ├─ Write JSON to file
    └─ Return success/failure
    ↓
Display confirmation
    ↓
Application exits
```

---

## Database Schema

### Future SQL Implementation

While the current implementation uses in-memory HashMap and JSON files, here's the proposed schema for database integration:

```sql
-- Customers Table
CREATE TABLE customers (
    customer_id VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    join_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts Table
CREATE TABLE accounts (
    account_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(20) NOT NULL,
    account_holder VARCHAR(100) NOT NULL,
    account_type ENUM('SAVINGS', 'CHECKING') NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CHECK (balance >= 0)
);

-- Transactions Table
CREATE TABLE transactions (
    transaction_id VARCHAR(50) PRIMARY KEY,
    account_id VARCHAR(50) NOT NULL,
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER_IN', 'TRANSFER_OUT') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    description VARCHAR(255),
    related_account_id VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CHECK (amount > 0)
);

-- Indices for performance
CREATE INDEX idx_customer_email ON customers(email);
CREATE INDEX idx_account_customer ON accounts(customer_id);
CREATE INDEX idx_transaction_account ON transactions(account_id);
CREATE INDEX idx_transaction_timestamp ON transactions(timestamp);
```

---

## Exception Hierarchy

```
java.lang.Exception
    │
    ├── InsufficientFundsException
    │   └── Contains: accountId, requestedAmount, availableBalance
    │   └── Thrown: During withdrawal/transfer with insufficient balance
    │
    ├── AccountNotFoundException
    │   └── Contains: accountId
    │   └── Thrown: When accessing non-existent account
    │
    └── InvalidInputException
        └── Contains: fieldName, invalidValue, reason
        └── Thrown: When input validation fails
```

---

## Best Practices Implemented

### 1. **Single Responsibility Principle (SRP)**
Each class has one reason to change:
- `Account`: Manages account state
- `Transaction`: Represents a transaction
- `AccountService`: Manages account operations
- `ConsoleUtil`: Handles CLI operations
- `ValidationUtil`: Performs validations

### 2. **Open/Closed Principle (OCP)**
Open for extension, closed for modification:
- `Account` abstract class allows new account types without modifying existing code
- New transaction types can be added via enum

### 3. **Liskov Substitution Principle (LSP)**
Subclasses can substitute parent without breaking functionality:
```java
Account account = new SavingsAccount(...);  // or CheckingAccount
account.deposit(amount);  // Works with both
account.withdraw(amount); // Different behavior, same contract
```

### 4. **Interface Segregation Principle (ISP)**
Classes depend only on methods they use:
- `ValidationUtil` has focused static methods
- `FileManager` exposes only relevant persistence methods

### 5. **Dependency Inversion Principle (DIP)**
Depend on abstractions, not concrete implementations:
- `Account` abstract class, not specific implementations
- Service layer abstracts business logic from presentation

### 6. **DRY (Don't Repeat Yourself)**
Eliminate code duplication:
- Common validation in `ValidationUtil`
- Common account logic in abstract `Account` class
- Shared exception handling

### 7. **SOLID Principles Summary**
✅ Single Responsibility Principle  
✅ Open/Closed Principle  
✅ Liskov Substitution Principle  
✅ Interface Segregation Principle  
✅ Dependency Inversion Principle  

### 8. **Clean Code Practices**
- Meaningful variable and method names
- Short, focused methods
- Comprehensive documentation
- Proper exception handling
- Input validation at boundaries
- Immutable objects where appropriate

---

## Performance Considerations

### Data Structure Choices

**HashMap for Account Storage**
```
Complexity: O(1) average for get/put/remove
Reason: Fast lookup by accountId
Alternative: TreeMap (O(log n)) - unnecessary overhead
```

**ArrayList for Transaction History**
```
Complexity: O(1) append, O(n) search
Reason: Mostly append operations
Alternative: LinkedList - worse performance
```

**Stream API for Filtering**
```java
// Efficient filtering
accounts.values().stream()
    .filter(a -> a.getType() == type)
    .collect(Collectors.toList());
```

### Memory Usage

**BigDecimal over Double**
- Exact decimal representation
- No floating-point precision errors
- Slightly higher memory (trade-off for correctness)

**Immutable Transactions**
- No defensive copying needed
- Safe for concurrent access (future enhancement)
- Allows caching

---

## Thread Safety Considerations

### Current Implementation
- Not thread-safe (single-threaded CLI)
- In-memory HashMap shared state

### Future Enhancement: Multi-threading
```java
// Consider for multi-threaded scenarios
private final Map<String, Account> accounts = 
    Collections.synchronizedMap(new HashMap<>());

// Or use ConcurrentHashMap
private final Map<String, Account> accounts = 
    new ConcurrentHashMap<>();

// Synchronize sensitive operations
private synchronized static String generateAccountId() { ... }
```

---

## Scalability Roadmap

### Phase 1: Current (CLI, In-Memory)
- Single-threaded
- In-memory HashMap
- JSON file persistence

### Phase 2: Database Integration
- Replace HashMap with database persistence
- Add connection pooling
- Implement ORM (JPA/Hibernate)

### Phase 3: REST API
- Spring Boot REST endpoints
- JSON/XML serialization
- API documentation (Swagger/OpenAPI)

### Phase 4: Microservices
- Account service microservice
- Transaction service microservice
- Customer service microservice
- Event-driven architecture (Kafka)

### Phase 5: Web/Mobile
- React/Angular frontend
- Mobile app integration
- Real-time notifications

---

**Last Updated**: May 8, 2026  
**Document Version**: 1.0.0  
**Status**: Complete
