import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.banking.models.Account;
import com.banking.services.AccountService;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.AccountNotFoundException;

public class AccountServiceTest {
    private AccountService accountService;
    private Account account;

    @BeforeEach
    public void setUp() {
        accountService = new AccountService();
        account = new Account("12345", "John Doe", 1000.0);
        accountService.createAccount(account);
    }

    @Test
    public void testDeposit() {
        accountService.deposit("12345", 500.0);
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void testWithdraw() throws InsufficientFundsException {
        accountService.withdraw("12345", 300.0);
        assertEquals(700.0, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw("12345", 1200.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    public void testTransfer() throws InsufficientFundsException, AccountNotFoundException {
        Account recipient = new Account("67890", "Jane Doe", 500.0);
        accountService.createAccount(recipient);
        accountService.transfer("12345", "67890", 200.0);
        
        assertEquals(800.0, account.getBalance());
        assertEquals(700.0, recipient.getBalance());
    }

    @Test
    public void testAccountNotFound() {
        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            accountService.withdraw("99999", 100.0);
        });
        assertEquals("Account not found", exception.getMessage());
    }
}