package pl.bartekk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.bartekk.model.User;
import pl.bartekk.repository.UserDao;

public class UserServiceTest {

    @InjectMocks
    private UserService userService = UserService.getInstance();

    @Mock
    private UserDao userDao;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createNewUserTest() {
        // given
        String testName = "TestName";
        User newUser = new User(testName);
        // when
        when(userDao.insertUser(any())).thenReturn(true);
        // then
        assertTrue(userService.createNewUser(testName));
    }

    @Test
    public void getUserTest() {
        // given
        String testUserName = "Test User";
        User testUser = new User(testUserName);
        // when
        when(userDao.getUser(testUserName)).thenReturn(testUser);
        // then
        assertEquals(userService.getUser(testUserName), testUser);
    }

    @Test
    public void getAllUsersTest_whenNoAnyUser() {
        // given
        List<User> emptyUserList = new ArrayList<>();
        // when
        when(userDao.getAllUsers()).thenReturn(emptyUserList);
        // then
        int expectedSize = 0;
        assertEquals(userService.getAllUsers().size(), expectedSize);
    }
}
