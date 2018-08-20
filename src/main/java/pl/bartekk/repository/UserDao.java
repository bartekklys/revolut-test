package pl.bartekk.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pl.bartekk.exception.UserExistsException;
import pl.bartekk.exception.UserNotFoundException;
import pl.bartekk.model.User;

public class UserDao {

    private Session session;
    private Transaction transaction;
    private static UserDao instance;

    // in-memory datastore (requirement #3)
    private static final Map<String, User> userRepository = new HashMap<>();

    private UserDao() {
    }

    //create an singleton object
    public static UserDao getInstance() {
        return instance == null ? instance = new UserDao() : instance;
    }

    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    private Session openSession() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    private void closeSession() {
        transaction.commit();
        session.close();
    }

    public boolean insertUser(User user) {
        if (!userExists(user.getName())) {
            openSession().save(new User(user.getName()));
            closeSession();
            return true;
        } else {
            throw new UserExistsException("User with that name already exists.");
        }
    }

    public User getUser(String name) {
        return Optional.ofNullable(userRepository.get(name)).orElseThrow(() -> new UserNotFoundException("That user has not been found."));
    }

    public List<User> getAllUsers() {
        openSession();
        List list = session.createCriteria(User.class).list();
        closeSession();
        return list;
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
