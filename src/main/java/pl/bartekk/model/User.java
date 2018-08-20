package pl.bartekk.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column
    @Getter
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter
    private Account account;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.account = new Account();
    }
}