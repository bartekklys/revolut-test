package pl.bartekk.service;

import java.math.BigDecimal;
import pl.bartekk.exception.NotEnoughFundsException;
import pl.bartekk.model.Account;
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

    public boolean updateBalance(String name, BigDecimal amount) throws NotEnoughFundsException {
        return userDao.updateBalance(name, amount);
    }

    public void transferMoney(String from, String to, BigDecimal amount) throws NotEnoughFundsException {
        User fromUser = userDao.getUser(from);
        Account fromAccount = fromUser.getAccount();
        if (fromAccount.getBalance().compareTo(amount) >= 0) {
            userDao.transferMoney(from, to, amount);
        } else {
            throw new NotEnoughFundsException("This account has not enough funds to perform this operation");
        }
    }
}
