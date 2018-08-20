package pl.bartekk.service;

import java.util.List;
import pl.bartekk.model.User;
import pl.bartekk.repository.UserDao;

public class UserService {

    private UserDao userDao = UserDao.getInstance();
    private static UserService instance;

    //create an singleton object
    public static UserService getInstance() {
        return instance == null ? instance = new UserService() : instance;
    }

    public boolean createNewUser(String name) {
        return userDao.insertUser(new User(name));
    }

    public User getUser(String name) {
        return userDao.getUser(name);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
