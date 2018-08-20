package pl.bartekk.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserTest {

    private User user;

    @BeforeMethod
    public void createUser() {
        user = new User("TestName");
    }

    @Test
    public void newUserShouldHaveAnAccount() {
        Assert.assertNotNull(user.getAccount());
    }
}
