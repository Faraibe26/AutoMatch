import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.banking.models.Account;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("12345", "John Doe", 1000.0);
    }

    @Test
    public void testDeposit() {
        account.deposit(500.0);
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void testWithdraw() {
        account.withdraw(300.0);
        assertEquals(700.0, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(1100.0);
        });
        assertEquals("Insufficient funds for withdrawal", exception.getMessage());
    }

    @Test
    public void testTransfer() {
        Account targetAccount = new Account("67890", "Jane Doe", 500.0);
        account.transfer(targetAccount, 200.0);
        assertEquals(800.0, account.getBalance());
        assertEquals(700.0, targetAccount.getBalance());
    }

    @Test
    public void testTransferInsufficientFunds() {
        Account targetAccount = new Account("67890", "Jane Doe", 500.0);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.transfer(targetAccount, 1100.0);
        });
        assertEquals("Insufficient funds for transfer", exception.getMessage());
    }
}