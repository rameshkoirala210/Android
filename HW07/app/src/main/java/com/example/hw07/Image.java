package com.example.hw07;

import java.util.ArrayList;

public class Image {
    private ArrayList<String> comments;
    private String documentID;
    private ArrayList<String> likedBy;
    private String postedUUID;
    private String storageRef;

    public Image() {
    }

    public Image(String documentID, ArrayList<String> likedBy, String storageRef, ArrayList<String> comments, String postedUUID) {
        this.documentID = documentID;
        this.likedBy = likedBy;
        this.storageRef = storageRef;
        this.comments = comments;
        this.postedUUID = postedUUID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getStorageRef() {
        return storageRef;
    }

    public void setStorageRef(String storageRef) {
        this.storageRef = storageRef;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public String getPostedUUID() {
        return postedUUID;
    }

    public void setPostedUUID(String postedUUID) {
        this.postedUUID = postedUUID;
    }

    @Override
    public String toString() {
        return "Image{" +
                "documentID='" + documentID + '\'' +
                ", likedBy=" + likedBy +
                ", storageRef='" + storageRef + '\'' +
                ", comments=" + comments +
                ", postedUUID='" + postedUUID + '\'' +
                '}';
    }
}
