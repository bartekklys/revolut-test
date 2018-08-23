package pl.bartekk.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import pl.bartekk.exception.UserExistsException;
import pl.bartekk.exception.UserNotFoundException;
import pl.bartekk.model.Account;
import pl.bartekk.model.User;

@Slf4j
public class UserDao {

    private static Session session;
    private static Transaction transaction;
    private static UserDao instance;

    private UserDao() {
    }

    //create an singleton object
    public static UserDao getInstance() {
        return instance == null ? instance = new UserDao() : instance;
    }

    private static SessionFactory getSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            log.error(String.format("Failed to create sessionFactory object. %s", ex));
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session openSession() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        transaction.commit();
        session.close();
    }

    /**
     * Persist the given {@link User} instance into datastore.
     *
     * @param user instance of a persistent class {@link User}
     * @return true if user is saved successfully, false otherwise
     */
    public boolean insertUser(User user) throws UserExistsException {
        if (!userExists(user.getName())) {
            openSession().save(new User(user.getName()));
            closeSession();
            log.info(String.format("User %s has been successfully created.", user.getName()));
            return true;
        } else {
            log.error(String.format("User with name %s already exists in a datastore.", user.getName()));
            throw new UserExistsException("User with that name already exists.");
        }
    }

    /**
     * Retrieve user with specified name form datastore.
     *
     * @param name
     * @return User with specified name
     */
    public User getUser(String name) {
        if (transaction == null || transaction.getStatus() != TransactionStatus.ACTIVE) {
            openSession();
        }
        Query query = session.createQuery("from User where name=:name");
        query.setParameter("name", name);
        Optional<User> user = query.uniqueResultOptional();
        log.info(String.format("Retrieved user %s", user));
        return user.orElseThrow(() -> new UserNotFoundException("That user has not been found."));
    }

    /**
     * Retrieve list of all users available in datastore.
     *
     * @return list of all users
     */
    public List<User> getAllUsers() {
        CriteriaBuilder builder = openSession().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        criteriaQuery.from(User.class);
        List<User> users = openSession().createQuery(criteriaQuery).getResultList();
        log.info(String.format("Successfully retrieved list of %s users", users.size()));
        closeSession();
        return users;
    }

    /**
     * Check if user with specified name exists in datastore.
     *
     * @param name name of the user
     * @return true if user exists
     */
    private boolean userExists(String name) {
        try {
            return getUser(name) != null;
        } catch (UserNotFoundException e) {
            log.error(String.format("User %s does not exist.", name));
            return false;
        }
    }

    /**
     * Remove a persistent {@link User} instance from the datastore.
     *
     * @param name name of the user to be removed
     */
    public boolean removeUser(String name) {
        User user = getUser(name);
        try {
            session.delete(user);
            log.info(String.format("User %s has been successfully removed from datastore.", user.getName()));
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            closeSession();
        }
        return false;
    }

    /**
     * Update account balance of specified user. This method handles only deposit and withdraw
     * events.
     *
     * @param name   name of the user on which we want to perform an operation
     * @param amount amount of the money we want to apply
     */
    public boolean updateBalance(String name, BigDecimal amount) {
        User user = getUser(name);
        Account account = user.getAccount();
        account.updateBalance(amount);
        session.update(account);
        closeSession();
        log.info(String.format("Balance of user %s has been updated by amount %s.", user, amount));
        return true;
    }

    /**
     * Update two specified accounts with particular amount of money.
     *
     * @param fromUser   The user whose account should be debit
     * @param to     The account to top up
     * @param amount value of the transaction
     */
    public void transferMoney(User fromUser, String to, BigDecimal amount) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User toUser = getUser(to);
            Account fromAccount = fromUser.getAccount();
            Account toAccount = toUser.getAccount();
            fromAccount.updateBalance(amount.negate());
            toAccount.updateBalance(amount);
            session.update(fromAccount);
            session.update(toAccount);
            log.info(String.format("Amount %s has been succesfuuly transfered from account %s to account %s.",
                amount, fromUser, to));
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            closeSession();
        }
    }
}
