package com.example.hw07;

import java.util.ArrayList;

public class Profile {
    String UUID;
    String documentID;
    String name;
    String email;
    ArrayList<String> images;

    public Profile() {
    }

    public Profile(String UUID, String documentID, String name, String email, ArrayList<String> imagesReference) {
        this.UUID = UUID;
        this.documentID = documentID;
        this.name = name;
        this.email = email;
        this.images = imagesReference;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> imagesReference) {
        this.images = imagesReference;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "UUID='" + UUID + '\'' +
                ", documentID='" + documentID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", imagesReference=" + images +
                '}';
    }
}
