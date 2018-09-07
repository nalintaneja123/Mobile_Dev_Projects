package com.example.inclass11;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saipavanraju on 4/9/18.
 */

public class Images implements Serializable {

    ArrayList<String> urls = new ArrayList<String>();


    public ArrayList<String> getUrls() {
        return this.urls;
    }

    public void setUrls(String url) {
        this.urls.add(url);
    }
}
