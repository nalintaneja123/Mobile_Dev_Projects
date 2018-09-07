package com.example.inclass03;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nalin on 1/29/2018.
 */

public class Student implements Parcelable {

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

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String name;
    public String email;
    public String department;
    public String mood;

    public Student(String name, String email, String department, String mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    protected Student(Parcel in) {
        name = in.readString();
        email = in.readString();
        department = in.readString();
        mood = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood='" + mood + '\'' +
                '}';

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(department);
        parcel.writeString(mood);
    }

    @Override
    public int describeContents() {
        return 0;
    }



}
