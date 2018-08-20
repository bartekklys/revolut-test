package pl.bartekk.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import pl.bartekk.exception.UserExistsException;
import pl.bartekk.exception.UserNotFoundException;
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

    public boolean insertUser(User user) {
        if (!userExists(user.getName())) {
            userRepository.put(user.getName(), user);
            return true;
        } else {
            throw new UserExistsException("User with that name already exists.");
        }
    }

    public User getUser(String name) {
        return Optional.ofNullable(userRepository.get(name)).orElseThrow(() -> new UserNotFoundException("That user has not been found."));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.values());
    }

    private boolean userExists(String name) {
        return userRepository.containsKey(name);
    }

    public boolean removeUser(String name) {
        if (userRepository.containsKey(name)) {
            userRepository.remove(name);
            return true;
        } else {
            return false;
        }
    }
}
