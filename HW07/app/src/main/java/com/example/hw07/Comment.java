package com.example.hw07;

import com.google.firebase.Timestamp;

public class Comment {

    String commentID;
    String createdByName;
    String createdByUUID;
    Timestamp datetime;
    String description;
    String imageID;

    public Comment(String commentID, String createdByName, String createdByUUID, Timestamp datetime, String description, String imageID) {
        this.commentID = commentID;
        this.createdByName = createdByName;
        this.createdByUUID = createdByUUID;
        this.datetime = datetime;
        this.description = description;
        this.imageID = imageID;
    }

    public Comment() {
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedByUUID() {
        return createdByUUID;
    }

    public void setCreatedByUUID(String createdByUUID) {
        this.createdByUUID = createdByUUID;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getimageID() {
        return imageID;
    }

    public void setForumID(String imageID) {
        this.imageID = imageID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID='" + commentID + '\'' +
                ", createdByName='" + createdByName + '\'' +
                ", createdByUUID='" + createdByUUID + '\'' +
                ", datetime=" + datetime +
                ", description='" + description + '\'' +
                ", forumID='" + imageID + '\'' +
                '}';
    }
}