package com.example.group25hw04;

/**
 * Created by nalin on 2/21/2018.
 */

public class News {

    String newsTitle;
    String publishedat;
    String newsImage;
    String newsDescription;



    @Override
    public String toString() {
        return "News{" +
                "newsTitle='" + newsTitle + '\'' +
                ", publishedat='" + publishedat + '\'' +
                ", newsImage='" + newsImage + '\'' +
                ", newsDescription='" + newsDescription + '\'' +
                '}';
    }

   /* public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getPublishedat() {
        return publishedat;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }*/
}
