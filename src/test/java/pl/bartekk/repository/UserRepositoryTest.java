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

public class UserRepositoryTest {

    private UserRepository userRepository;
    private User user;
    private String testUserName = "TestUserName";

    @BeforeMethod
    public void setUp() {
        // given
        userRepository = UserRepository.getInstance();
        user = new User(testUserName);
        userRepository.removeUser(testUserName);
    }

    @Test
    public void insertUserTest() {
        // when
        boolean result = userRepository.insertUser(user);
        // then
        assertTrue(result);
    }

    @Test
    public void removeUserTest() {
        // given
        userRepository.insertUser(user);
        // when
        boolean result = userRepository.removeUser(testUserName);
        // then
        assertTrue(result);
    }

    @Test(expectedExceptions = UserExistsException.class)
    public void insertUserTest_shouldThrowExceptionWhenUserAlreadyExists() {
        // when
        userRepository.insertUser(user);
        boolean result = userRepository.insertUser(user);
        // then
        assertTrue(result);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void getUserTest_shouldThrowExceptionWhenUserNotExists() {
        userRepository.getUser("NoExists");
    }

    @Test
    public void getUserTest() {
        // given
        User user = new User(testUserName);
        userRepository.insertUser(user);
        // when
        User resultUser = userRepository.getUser(testUserName);
        // then
        assertNotNull(user);
    }

    @Test
    public void getAllUsersTes() {
        // when
        List<User> allUsers = userRepository.getAllUsers();
        // then
        assertThat(allUsers.size()).isEqualTo(0);
    }
}
