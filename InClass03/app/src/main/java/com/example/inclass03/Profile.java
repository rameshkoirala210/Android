package com.example.inclass03;

import java.io.Serializable;
/*
    Assignment # In Class Assignment 03
    File Name Serialization
    Full name of the student - Ramesh Koirala, Anirudh Shankar

 */
public class Profile implements Serializable {
    String name, id, email, department;

    public Profile(String name, String email,String id, String department) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }
}
