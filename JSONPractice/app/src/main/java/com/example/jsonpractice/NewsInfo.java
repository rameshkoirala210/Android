package com.example.jsonpractice;

import android.media.Image;

public class NewsInfo {
    String title, publishAt, description;
    Image newsImage;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "title='" + title + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", description='" + description + '\'' +
                ", newsImage=" + newsImage +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(Image newsImage) {
        this.newsImage = newsImage;
    }
}
