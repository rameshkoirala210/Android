package com.example.homework02;

import java.io.Serializable;
import java.util.Calendar;

public class Profile implements Serializable {
    String name, priority;
    Calendar date;

    public Profile(String name, Calendar date,String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

}
