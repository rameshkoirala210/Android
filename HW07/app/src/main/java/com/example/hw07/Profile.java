package com.example.hw07;

import java.util.ArrayList;

public class Profile {
    String UUID;
    String documentID;
    String name;
    String email;

    public Profile() {
    }

    public Profile(String UUID, String documentID, String name, String email) {
        this.UUID = UUID;
        this.documentID = documentID;
        this.name = name;
        this.email = email;
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


    @Override
    public String toString() {
        return "Profile{" +
                "UUID='" + UUID + '\'' +
                ", documentID='" + documentID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email +
                '}';
    }
}
