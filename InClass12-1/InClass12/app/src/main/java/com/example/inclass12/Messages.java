package com.example.inclass12;

/**
 * Created by nalin on 4/18/2018.
 */

public class Messages {

    String messageTitle;
    String created_at;
//    String user_fname;
//    String user_lname;
//    String username;
    String user_id;

    String messageKey;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return messageTitle;
    }

    public void setMessage(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//        public String getUser_fname() {
//        return user_fname;
//    }
//
//    public void setUser_fname(String user_fname) {
//        this.user_fname = user_fname;
//    }
//
//    public String getUser_lname() {
//        return user_lname;
//    }
//
//    public void setUser_lname(String user_lname) {
//        this.user_lname = user_lname;
//    }


    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
