package pl.bartekk.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
            System.err.println("Failed to create sessionFactory object." + ex);
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
            return true;
        } else {
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
        return true;
    }

    /**
     * Update two specified accounts with particular amount of money.
     *
     * @param from   The account from which the amount is to be deducted
     * @param to     The account to top up
     * @param amount value of the transaction
     */
    public void transferMoney(String from, String to, BigDecimal amount) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User formUser = getUser(from);
            User toUser = getUser(to);
            Account fromAccount = formUser.getAccount();
            Account toAccount = toUser.getAccount();
            fromAccount.updateBalance(amount.negate());
            toAccount.updateBalance(amount);
            session.update(fromAccount);
            session.update(toAccount);
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
