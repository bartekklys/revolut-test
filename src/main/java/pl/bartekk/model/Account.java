package pl.bartekk.model;

import java.util.UUID;
import lombok.Getter;
import pl.bartekk.exception.NotEnoughFundsException;

public class Account {

    @Getter
    private String accountNumber;

    @Getter
    private double balance;

    public Account() {
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = 0.0;
    }

    public void addMoney(double amount) {
        balance = balance + amount;
    }

    public void subtractMoney(double amount) {
        if (balance < amount) {
            throw new NotEnoughFundsException("This account has not enough funds to perform this operation");
        } else {
            balance = balance - amount;
        }
    }
}
