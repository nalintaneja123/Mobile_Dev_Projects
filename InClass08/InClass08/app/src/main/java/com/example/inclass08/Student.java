package com.example.inclass08;

import java.io.Serializable;

/**
 * Created by nalin on 3/19/2018.
 */

public class Student implements Serializable {

    String name;
    String email;
    String department;
    String valueOfMood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getValueOfMood() {
        return valueOfMood;
    }

    public void setValueOfMood(String valueOfMood) {
        this.valueOfMood = valueOfMood;
    }
}
