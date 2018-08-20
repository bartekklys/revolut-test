package pl.bartekk.service;

import java.util.List;
import pl.bartekk.model.User;
import pl.bartekk.repository.UserRepository;

public class UserService {

    private UserRepository userRepository = UserRepository.getInstance();
    private static UserService instance;

    //create an singleton object
    public static UserService getInstance() {
        return instance == null ? instance = new UserService() : instance;
    }

    public boolean createNewUser(String name) {
        return userRepository.insertUser(new User(name));
    }

    public User getUser(String name) {
        return userRepository.getUser(name);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
