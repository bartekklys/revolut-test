package pl.bartekk.model;

import lombok.Getter;

public class User {

    @Getter
    private String name;

    @Getter
    private Account account;

    public User(String name) {
        this.name = name;
        this.account = new Account();
    }
}