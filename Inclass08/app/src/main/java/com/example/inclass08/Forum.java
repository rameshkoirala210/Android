package com.example.inclass08;

import java.sql.Timestamp;

public class Forum {
    String createdbyName, description, title;
    Timestamp datetime;

    public Forum(String createdbyName, String description, String title, Timestamp daetime) {
        this.createdbyName = createdbyName;
        this.description = description;
        this.title = title;
        this.datetime = datetime;
    }
    public Forum(){

    }

    @Override
    public String toString() {
        return "Forum{" +
                "createdbyName='" + createdbyName + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", daetime=" + datetime +
                '}';
    }

    public String getCreatedbyName() {
        return createdbyName;
    }

    public void setCreatedbyName(String createdbyName) {
        this.createdbyName = createdbyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
