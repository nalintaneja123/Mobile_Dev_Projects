package com.example.group25hw03;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nalin on 2/15/2018.
 */

public class Question implements Serializable {

    String questionID;
    String URL;
    String correctanswer;
    String Questions;

    ArrayList<String> choices=new ArrayList<String>();


    @Override
    public String toString() {
        return "Question{" +
                "questionID='" + questionID + '\'' +
                ", URL='" + URL + '\'' +
                ", correctanswer='" + correctanswer + '\'' +
                ", Questions='" + Questions + '\'' +
                ", choices=" + choices +
                '}';
    }

    public Question(String questionID, String URL, String correctanswer, String questions, ArrayList<String> choices) {
        this.questionID=questionID;
        this.URL = URL;
        this.correctanswer = correctanswer;
        Questions = questions;
        this.choices = choices;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }
}
