package pl.bartekk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.math.BigDecimal;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.bartekk.exception.NotEnoughFundsException;
import pl.bartekk.exception.UserNotFoundException;
import pl.bartekk.model.User;
import pl.bartekk.repository.UserDao;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService = AccountService.getInstance();

    @Mock
    private UserDao userDao;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void updateBalanceTest() {
        // given
        String testUserName = "TestUserName";
        // when
        when(userDao.updateBalance(any(), any())).thenReturn(true);
        boolean result = accountService.updateBalance(testUserName, BigDecimal.TEN);
        // then
        Assert.assertTrue(result);
    }

    @Test
    public void transferMoneyTest() {
        // given
        String testUserName = "TestUser";
        User user = new User(testUserName);
        BigDecimal amount = BigDecimal.TEN;
        user.getAccount().updateBalance(amount);
        // when
        when(userDao.getUser(any())).thenReturn(user);
        doNothing().when(userDao).transferMoney(any(), any(), any());
        accountService.transferMoney("from", "to", amount);
        // then
        verify(userDao, times(1)).transferMoney(any(), anyString(), any());
    }

    @Test(expectedExceptions = NotEnoughFundsException.class)
    public void transferMoneyTest_notEnoughFundsException() {
        // given
        String testUserName = "TestUser";
        User user = new User(testUserName);
        BigDecimal amount = BigDecimal.TEN;
        // when
        when(userDao.getUser(any())).thenReturn(user);
        doNothing().when(userDao).transferMoney(any(), any(), any());
        accountService.transferMoney("from", "to", amount);
    }

    @Test
    public void transferMoneyTest_userNotFound() {
        // given
        BigDecimal amount = BigDecimal.TEN;
        // when
        when(userDao.getUser(any())).thenThrow(UserNotFoundException.class);
        accountService.transferMoney("from", "to", amount);
        // then
        verify(userDao, times(0)).transferMoney(any(), anyString(), any());
    }
}
