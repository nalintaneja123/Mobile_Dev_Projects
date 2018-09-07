package com.example.inclass13;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by nalin on 4/23/2018.
 */

@IgnoreExtraProperties
public class User implements Serializable {

   public String firstname;
   public String lastname;


    public User()
    {

    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
