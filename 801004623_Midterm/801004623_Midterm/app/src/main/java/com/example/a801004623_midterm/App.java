package com.example.a801004623_midterm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nalin on 3/12/2018.
 */

public class App implements Serializable {



    public String appName;
    public String ArtistName;
    public String ReleaseDate;
    public String appImage;

    ArrayList<String> genres=new ArrayList<>();

    public String appCopyright;



    @Override
    public String toString() {
        return "App{" +
                "appName='" + appName + '\'' +
                ", ArtistName='" + ArtistName + '\'' +
                ", ReleaseDate='" + ReleaseDate + '\'' +
                ", appImage='" + appImage + '\'' +
                ", genres=" + genres +
                ", appCopyright='" + appCopyright + '\'' +
                '}';
    }
}
