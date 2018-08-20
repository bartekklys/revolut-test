package pl.bartekk.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class User {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Account account;

    public User(String name) {
        this.name = name;
        this.account = new Account();
    }
}