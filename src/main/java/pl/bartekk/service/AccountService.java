package pl.bartekk.service;

import java.math.BigDecimal;
import pl.bartekk.model.User;
import pl.bartekk.repository.UserDao;

public class AccountService {

    private UserDao userDao = UserDao.getInstance();
    private static AccountService instance;

    private AccountService() {
    }

    public static AccountService getInstance() {
        return instance == null ? instance = new AccountService() : instance;
    }

    public void updateBalance(String name, BigDecimal amount) {
        userDao.updateBalance(name, amount);
    }

    public void transferMoney(String from, String to, BigDecimal amount) {
        User userFrom = userDao.getUser(from);
        User userTo = userDao.getUser(to);
        userDao.updateBalance(userFrom.getName(), amount.negate());
        userDao.updateBalance(userTo.getName(), amount);
    }
}
