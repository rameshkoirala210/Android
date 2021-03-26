package com.example.midtermreview;


public class User {
    String name,gender,group,state,state_abv;
    int age;

    public User(String name, int age, String gender, String group, String state, String state_abv) {
        this.name = name;
        this.gender = gender;
        this.group = group;
        this.state = state;
        this.state_abv = state_abv;
        this.age = age;
    }
}
