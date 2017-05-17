package com.example.he.myim.evenbus;

/**
 * Created by je on 12/05/17.
 */

public class User {
    public String userName;

    public User(String userName, String date) {
        this.userName = userName;
        this.date = date;
    }

    public String date;

    public User(String userName) {
        this.userName = userName;
    }
}
