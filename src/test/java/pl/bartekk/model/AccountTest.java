package pl.bartekk.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.math.BigDecimal;
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
        BigDecimal expectedBalance = BigDecimal.ZERO;
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test
    public void updateBalanceTest_deposit() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        // when
        account.updateBalance(amount);
        // then
        assertEquals(account.getBalance(), BigDecimal.TEN);
    }

    @Test
    public void updateBalanceTest_withdraw() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        account.updateBalance(amount);
        // when
        account.updateBalance(BigDecimal.ONE.negate());
        // then
        assertEquals(account.getBalance(), new BigDecimal(9));
    }

    @Test(expectedExceptions = NotEnoughFundsException.class)
    public void updateBalanceTest_withdraw_noFunds() {
        // when
        account.updateBalance(BigDecimal.TEN.negate());
    }
}
