package pl.bartekk.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import pl.bartekk.exception.NotEnoughFundsException;

public class Account {

    @Getter
    private String accountNumber;

    @Getter
    private BigDecimal balance;

    public Account() {
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
    }

    public void addMoney(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtractMoney(BigDecimal amount) {
        if (hasSufficientFunds(amount)) {
            balance = balance.subtract(amount);
        } else {
            throw new NotEnoughFundsException("This account has not enough funds to perform this operation");
        }
    }

    private boolean hasSufficientFunds(BigDecimal amount) {
        return balance.compareTo(amount) == 1;
    }
}
