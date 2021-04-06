package com.example.hw06;


import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Forum {
    String UUID, createdbyName;
    Timestamp datetime;
    String description;
    String documentID;
    ArrayList<String> likedBy;
    String title;

    public Forum(String UUID, String createdbyName, Timestamp datetime, String description, String documentID, ArrayList<String> likedBy, String title) {
        this.UUID = UUID;
        this.createdbyName = createdbyName;
        this.datetime = datetime;
        this.description = description;
        this.documentID = documentID;
        this.likedBy = likedBy;
        this.title = title;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "UUID='" + UUID + '\'' +
                ", createdbyName='" + createdbyName + '\'' +
                ", datetime=" + datetime +
                ", description='" + description + '\'' +
                ", documentID='" + documentID + '\'' +
                ", title='" + title + '\'' +
                ", likedBy=" + likedBy +
                '}';
    }

    public Forum(){

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

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
