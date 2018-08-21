package pl.bartekk.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.math.BigDecimal;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    /*@Test
    public void addMoneyTest() {
        BigDecimal expectedBalance = BigDecimal.TEN;
        account.addMoney(BigDecimal.TEN);
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test
    public void subtractMoney() {
        BigDecimal expectedBalance = new BigDecimal(9.0);
        account.addMoney(BigDecimal.TEN);
        account.subtractMoney(BigDecimal.ONE);
        assertEquals(account.getBalance(), expectedBalance);
    }

    @Test(expectedExceptions = NotEnoughFundsException.class)
    public void shouldThrowExceptionWhenNotEnoughFunds() {
        account.addMoney(BigDecimal.ONE);
        account.subtractMoney(BigDecimal.TEN);
    }*/
}
