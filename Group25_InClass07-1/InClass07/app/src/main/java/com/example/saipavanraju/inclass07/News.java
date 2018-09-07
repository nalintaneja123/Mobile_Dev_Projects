package com.example.saipavanraju.inclass07;

import java.io.Serializable;

/**
 * Created by saipavanraju on 2/26/18.
 */


public class News implements Serializable{
    public String title;
    public String publishedAt;
    public String urlToImage;
    public String description;

    public News() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Articles{" +
                "title='" + title + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}