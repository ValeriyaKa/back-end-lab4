package com.example.weblab4.Database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String login;
    private String password;

    public User(){}

    public User(String log, String pass){
        this.login=log;
        this.password=pass;
    }
}
