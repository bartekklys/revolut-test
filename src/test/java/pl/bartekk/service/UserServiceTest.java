package pl.bartekk.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
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

    private User testUser;
    private String testUserName;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testUser = new User(testUserName);
        testUserName = "TestUserName";
    }

    @Test
    public void createNewUserTest() {
        // when
        when(userDao.insertUser(any())).thenReturn(true);
        // then
        assertTrue(userService.createNewUser(testUserName));
    }

    @Test
    public void getUserTest() {
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

    @Test
    public void removeUserTest() {
        // when
        when(userDao.removeUser(anyString())).thenReturn(true);
        boolean result = userService.removeUser(testUserName);
        // then
        assertTrue(result);
    }
}
