package com.example.homework02;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable, Comparable<Task> {
    // TODO: Change the class name to Task
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
        return
                "name='" + name + '\'' +
                "\n priority='" + priority + '\'' +
                "\n date=" + x;
    }
}
