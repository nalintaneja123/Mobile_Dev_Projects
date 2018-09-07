package com.example.inclass12;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by nalin on 4/19/2018.
 */

@IgnoreExtraProperties
public class  User implements Serializable {

    public  String firstname;
    public  String lastname;


    public User()
    {

    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
