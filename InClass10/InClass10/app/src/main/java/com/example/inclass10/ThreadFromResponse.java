package com.example.inclass10;

/**
 * Created by nalin on 4/3/2018.
 */

public class ThreadFromResponse {

    String title;
    String user_id;
    String  id;

    public String getUserID() {
        return user_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
