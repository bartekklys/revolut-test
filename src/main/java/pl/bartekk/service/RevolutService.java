package pl.bartekk.service;

import pl.bartekk.model.User;
import pl.bartekk.repository.UserRepository;

public class RevolutService {

    private UserRepository userRepository = UserRepository.getInstance();
    private static RevolutService instance;

    //create an singleton object
    public static RevolutService getInstance() {
        return instance == null ? instance = new RevolutService() : instance;
    }

    public void createNewUser(String name) {
        userRepository.insertUser(new User(name));
    }

    public User getUser(String name) {
        return userRepository.getUser(name);
    }
}
