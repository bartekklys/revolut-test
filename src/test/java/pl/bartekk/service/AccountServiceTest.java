package pl.bartekk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.math.BigDecimal;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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

    /*@Test
    public void transferMoneyTest() {
        // given
        String testUserName = "TestUser";
        User user = new User(testUserName);
        // when
        when(userDao.getUser(any())).thenReturn(user);
        when(userDao.updateBalance(any(), any())).thenReturn(true);
        accountService.transferMoney("from", "to", BigDecimal.TEN);
        // then
        // TODO: 21.08.2018
    }*/
}
