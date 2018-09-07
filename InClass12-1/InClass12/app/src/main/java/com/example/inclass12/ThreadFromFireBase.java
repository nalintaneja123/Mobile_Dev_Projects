package com.example.inclass12;

import java.io.Serializable;

/**
 * Created by nalin on 4/17/2018.
 */

public class ThreadFromFireBase implements Serializable {

    String title;
    String user_id;
    String key;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

