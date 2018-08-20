package pl.bartekk.repository;

import java.util.HashMap;
import java.util.Map;
import pl.bartekk.model.User;

public class UserRepository {

    private static UserRepository instance;

    // in-memory datastore (requirement #3)
    private static final Map<String, User> userRepository = new HashMap<>();

    private UserRepository() {
    }

    //create an singleton object
    public static UserRepository getInstance() {
        return instance == null ? instance = new UserRepository() : instance;
    }

    public void insertUser(User user) {
        userRepository.put(user.getName(), user);
    }

    public User getUser(String name) {
        return userRepository.get(name);
    }
}
