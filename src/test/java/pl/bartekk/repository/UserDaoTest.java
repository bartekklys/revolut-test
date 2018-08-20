package pl.bartekk.repository;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.bartekk.exception.UserExistsException;
import pl.bartekk.exception.UserNotFoundException;
import pl.bartekk.model.User;

public class UserDaoTest {

    private UserDao userDao;
    private User user;
    private String testUserName = "TestUserName";

    @BeforeMethod
    public void setUp() {
        // given
        userDao = UserDao.getInstance();
        user = new User(testUserName);
        userDao.removeUser(testUserName);
    }

    @Test
    public void insertUserTest() {
        // when
        boolean result = userDao.insertUser(user);
        // then
        assertTrue(result);
    }

    @Test
    public void removeUserTest() {
        // given
        userDao.insertUser(user);
        // when
        boolean result = userDao.removeUser(testUserName);
        // then
        assertTrue(result);
    }

    @Test(expectedExceptions = UserExistsException.class)
    public void insertUserTest_shouldThrowExceptionWhenUserAlreadyExists() {
        // when
        userDao.insertUser(user);
        boolean result = userDao.insertUser(user);
        // then
        assertTrue(result);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getUserTest_shouldThrowExceptionWhenUserNotExists() {
        userDao.getUser("NoExists");
    }

    @Test
    public void getUserTest() {
        // given
        User user = new User(testUserName);
        userDao.insertUser(user);
        // when
        User resultUser = userDao.getUser(testUserName);
        // then
        assertNotNull(user);
    }

    @Test
    public void getAllUsersTes() {
        // when
        List<User> allUsers = userDao.getAllUsers();
        // then
        assertThat(allUsers.size()).isEqualTo(0);
    }
}
