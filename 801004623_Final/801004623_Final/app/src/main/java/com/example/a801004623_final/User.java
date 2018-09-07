package com.example.a801004623_final;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by nalin on 5/7/2018.
 */
@IgnoreExtraProperties
public class User implements Serializable {

    public String username;
    public String password;


    public User()
    {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
}
}
