package com.example.hw07;

import java.util.ArrayList;

public class Image {
    String documentID;
    ArrayList<String> likedBy;
    String referenceLocation;

    public Image() {
    }

    public Image(String documentID, ArrayList<String> likedBy, String refLocation) {
        this.documentID = documentID;
        this.likedBy = likedBy;
        this.referenceLocation = refLocation;
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

    public String getReferenceLocation() {
        return referenceLocation;
    }

    public void setReferenceLocation(String refLocation) {
        this.referenceLocation = refLocation;
    }

    @Override
    public String toString() {
        return "Image{" +
                "documentID='" + documentID + '\'' +
                ", likedBy=" + likedBy +
                ", refLocation='" + referenceLocation + '\'' +
                '}';
    }
}
