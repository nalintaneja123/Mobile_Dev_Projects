package com.example.saipavanraju.hw2_group25;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by saipavanraju on 2/5/18.
 */

public class Task implements Parcelable,Comparable<Task> {

    public String title;
    public long dateTime;
    public String prioity;

    public Task(String title, long dateTime, String prioity ) {
        this.title = title;
        this.dateTime = dateTime;
        this.prioity = prioity;
    }

    protected Task(Parcel in) {
        this.title = in.readString();
        this.dateTime = in.readLong();
        this.prioity = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", date='" + dateTime + '\'' +
                ", prioity='" + prioity + '\'' +
                '}';
    }

    public String getPrioity() {
        return prioity;
    }

    public void setPrioity(String prioity) {
        this.prioity = prioity;
    }

    @Override
    public int describeContents() {

        return 0;

    }


    public int compareTo(Task task) {
        return Long.compare(this.dateTime, task.getDateTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(this.dateTime);
        dest.writeString(this.prioity);
    }
}
