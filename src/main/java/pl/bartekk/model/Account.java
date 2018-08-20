package pl.bartekk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import pl.bartekk.exception.NotEnoughFundsException;

@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column
    @Getter
    private String accountNumber;

    @Column
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
