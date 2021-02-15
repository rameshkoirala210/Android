package com.example.homework02;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
/*
    Assignment # Homework02
    File Name Tasks
    Full name of the student - Ramesh Koirala, Anirudh Shankar
*/
public class Task implements Serializable, Comparable<Task> {
    String name, priority;
    Calendar date;

    public Task(String name, Calendar date,String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
        if (this.date.after(o.date)){
            return 1;
        } else if(this.date.before(o.date)){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        String x = date.get(Calendar.MONTH)+1 + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR);
        return  "Upcoming Tasks\n\n" +
                name + '\'' +
                "\n" + x +
                "\t\t\t\t\t\t\t\t" + priority;
    }

    public boolean equals(Task o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals(o.name) &&
                priority.equals(o.priority) &&
                date.equals(o.date);
    }

}
