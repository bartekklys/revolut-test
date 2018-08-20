package pl.bartekk.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.bartekk.exception.NotEnoughFundsException;

public class AccountTest {

    private Account account;

    @BeforeMethod
    public void createAccount() {
        account = new Account();
    }

    @Test
    public void accountNumberShouldbeNotNull() {
        assertNotNull(account.getAccountNumber());
    }

    @Test
    public void newAccountBalanceShouldBeEqualToZeroTest() {
        double expectedBalance = 0.0;
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test
    public void addMoneyTest() {
        double expectedBalance = 10.0;
        account.addMoney(10.0);
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test
    public void subtractMoney() {
        double expectedBalance = 5.0;
        account.addMoney(10.0);
        account.subtractMoney(5.0);
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test(expectedExceptions = NotEnoughFundsException.class)
    public void shouldThrowExceptionWhenNotEnoughFunds() {
        account.addMoney(5.0);
        account.subtractMoney(10.0);
    }
}
