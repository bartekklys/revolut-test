package pl.bartekk.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTest {

    @Test
    public void newUserShouldHaveAnAccount() {
        User user = new User("TestName");
        Assert.assertNotNull(user.getAccount());
    }
}
