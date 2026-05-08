# Contributing to Banking Management System

Thank you for your interest in contributing to the Banking Management System! This document provides guidelines for contributing to the project.

## 📋 Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Workflow](#development-workflow)
4. [Coding Standards](#coding-standards)
5. [Commit Guidelines](#commit-guidelines)
6. [Pull Request Process](#pull-request-process)
7. [Testing](#testing)
8. [Documentation](#documentation)

## Code of Conduct

Be respectful, inclusive, and professional in all interactions. We're committed to providing a welcoming environment for all contributors.

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+ (optional, can use javac directly)
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Setup Development Environment

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/banking-management-system.git
cd banking-management-system

# Create a feature branch
git checkout -b feature/your-feature-name

# Build the project
javac -d build/classes -encoding UTF-8 \
  $(find src/main/java -name "*.java" -type f)

# Run the application
java -cp build/classes com.banking.BankingApp
```

## 🔄 Development Workflow

### 1. Fork and Clone

```bash
# Fork the repository on GitHub, then:
git clone https://github.com/YOUR_USERNAME/banking-management-system.git
cd banking-management-system
git remote add upstream https://github.com/ORIGINAL_OWNER/banking-management-system.git
```

### 2. Create Feature Branch

```bash
git checkout -b feature/add-interest-calculation
```

Branch naming convention:
- `feature/` - New features
- `fix/` - Bug fixes
- `docs/` - Documentation updates
- `refactor/` - Code refactoring
- `test/` - Test additions

### 3. Make Changes

Follow the coding standards (see below) when making changes.

### 4. Commit Changes

```bash
git add .
git commit -m "Add interest calculation feature to savings accounts"
```

### 5. Keep Branch Updated

```bash
git fetch upstream
git rebase upstream/main
```

### 6. Push and Create Pull Request

```bash
git push origin feature/add-interest-calculation
```

Then create a Pull Request on GitHub.

## 📝 Coding Standards

### Java Conventions

- **Package Names**: `com.banking.[module]` (lowercase)
- **Class Names**: `PascalCase` (e.g., `SavingsAccount`)
- **Method Names**: `camelCase` (e.g., `calculateInterest()`)
- **Constants**: `UPPER_SNAKE_CASE` (e.g., `MAX_WITHDRAWAL_LIMIT`)
- **Variables**: `camelCase` (e.g., `accountBalance`)

### Code Style

```java
// Good: Clear and well-documented
public class SavingsAccount extends Account {
    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("100.00");
    
    /**
     * Calculates interest on the account balance.
     * 
     * @param annualRate the annual interest rate
     * @return calculated interest amount
     */
    public BigDecimal calculateInterest(BigDecimal annualRate) {
        return getBalance().multiply(annualRate).divide(new BigDecimal("12"));
    }
}
```

### Guidelines

- ✅ Use meaningful variable names
- ✅ Write comments for complex logic
- ✅ Keep methods focused (single responsibility)
- ✅ Use appropriate access modifiers (private, protected, public)
- ✅ Avoid magic numbers (use constants)
- ✅ Handle exceptions appropriately
- ✅ Use Java Streams where applicable
- ❌ Don't use wildcard imports
- ❌ Don't create overly long methods (>50 lines)
- ❌ Don't ignore exceptions

### OOP Principles

Ensure your code follows SOLID principles:

- **S**ingle Responsibility Principle
- **O**pen/Closed Principle
- **L**iskov Substitution Principle
- **I**nterface Segregation Principle
- **D**ependency Inversion Principle

## 📌 Commit Guidelines

### Commit Message Format

```
<type>: <subject>

<body>

<footer>
```

### Type

- `feat:` - A new feature
- `fix:` - A bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, missing semicolons, etc.)
- `refactor:` - Code refactoring
- `test:` - Adding or updating tests
- `chore:` - Dependency updates, build configuration changes

### Subject

- Use imperative mood ("add" not "added" or "adds")
- Don't capitalize first letter
- Limit to 50 characters
- Don't end with period

### Body

- Explain what and why, not how
- Wrap at 72 characters
- Separate from subject with blank line

### Examples

```bash
# Good
git commit -m "feat: add interest calculation for savings accounts

Implement monthly interest calculation feature that:
- Calculates interest based on account balance
- Applies interest automatically each month
- Logs interest transactions

Closes #123"

# Good
git commit -m "fix: resolve insufficient funds exception on transfer

Fix incorrect balance comparison when transferring between accounts
that was causing false insufficient funds errors"

# Bad
git commit -m "updated code"
git commit -m "Fix bug"
```

## 🔀 Pull Request Process

### Before Creating PR

1. ✅ Ensure code compiles without errors
2. ✅ Add/update tests for new functionality
3. ✅ Update documentation if needed
4. ✅ Run existing tests to ensure nothing broke
5. ✅ Keep commits logical and squash if needed

### PR Description Template

```markdown
## Description
Brief description of the changes

## Type of Change
- [ ] New feature
- [ ] Bug fix
- [ ] Documentation update
- [ ] Refactoring

## Changes Made
- List specific changes
- Another change

## Testing
- [ ] Unit tests added
- [ ] Manual testing completed
- [ ] All tests pass

## Related Issues
Closes #123

## Screenshots (if applicable)
Add any relevant screenshots or output

## Checklist
- [ ] Code follows style guidelines
- [ ] Documentation updated
- [ ] No breaking changes
```

## 🧪 Testing

### Writing Tests

```java
public class SavingsAccountTest {
    
    private SavingsAccount account;
    
    @Before
    public void setUp() {
        account = new SavingsAccount("John Doe", new BigDecimal("1000.00"));
    }
    
    @Test
    public void testInterestCalculation() {
        BigDecimal interest = account.calculateInterest(new BigDecimal("0.05"));
        assertEquals(new BigDecimal("4.17"), interest);
    }
    
    @Test(expected = InvalidInputException.class)
    public void testNegativeDepositThrowsException() throws InvalidInputException {
        account.deposit(new BigDecimal("-100.00"));
    }
}
```

### Running Tests

```bash
# Compile tests
javac -d build/test-classes -encoding UTF-8 -cp build/classes \
  $(find src/test/java -name "*.java" -type f)

# Run specific test
java -cp build/classes:build/test-classes org.junit.runner.JUnitCore \
  com.banking.models.SavingsAccountTest
```

### Test Coverage

- Aim for at least 70% code coverage
- Test both success and failure scenarios
- Test edge cases and boundary conditions

## 📚 Documentation

### JavaDoc Comments

```java
/**
 * Transfers money from this account to another account.
 * 
 * @param amount the amount to transfer
 * @param recipientAccount the recipient account
 * @throws InvalidInputException if amount is invalid
 * @throws InsufficientFundsException if balance is insufficient
 */
public void transfer(BigDecimal amount, Account recipientAccount) 
        throws InvalidInputException, InsufficientFundsException {
    // Implementation
}
```

### README Updates

If your changes affect how to use the system, update the README:
- Installation instructions
- Usage examples
- Configuration options
- New features

### Architecture Documentation

Complex changes should update `ARCHITECTURE.md`:
- New design patterns used
- New components introduced
- Changes to existing architecture

## 📊 Review Process

1. **Automated Checks**: GitHub Actions runs build and tests
2. **Code Review**: Maintainers review code quality, style, and functionality
3. **Feedback**: Changes may be requested
4. **Approval**: Once approved, maintainer merges the PR
5. **Celebration**: Your contribution is now part of the project! 🎉

## 🐛 Reporting Bugs

Use GitHub Issues to report bugs:

1. Click "New Issue"
2. Choose "Bug report"
3. Provide:
   - Clear title
   - Description of the bug
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Java version and OS

## 💡 Feature Requests

1. Click "New Issue"
2. Choose "Feature request"
3. Provide:
   - Clear title
   - Description of the feature
   - Use cases
   - Why it's needed
   - Potential implementation approach

## 📞 Questions or Need Help?

- Open an issue with the "question" label
- Check existing issues and discussions
- Review the documentation files (README.md, ARCHITECTURE.md)

## 🎓 Learning Resources

- [Java Coding Guidelines](https://www.oracle.com/java/technologies/javase/codeconventions-136091.html)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Git Workflow](https://guides.github.com/introduction/flow/)
- [JavaDoc Guidelines](https://www.oracle.com/javase/javadoc/writingdoccomments/)

## 🙏 Thank You!

Your contributions help make this project better. Whether it's code, bug reports, or suggestions, we appreciate your involvement!

---

**Happy Contributing!** 🚀

For questions, email: [your-email@example.com]
