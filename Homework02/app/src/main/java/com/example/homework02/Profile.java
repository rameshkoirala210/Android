package com.example.homework02;

import java.io.Serializable;

public class Profile implements Serializable {
    String name, date, priority;

    public Profile(String name, String date,String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }
}
