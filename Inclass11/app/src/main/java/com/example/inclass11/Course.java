package com.example.inclass11;

import android.widget.RadioGroup;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo
    public String courseNumber;

    @ColumnInfo
    public String courseName;

    @ColumnInfo
    public String creditHours;

//    @ColumnInfo
//    public RadioGroup courseGrade;


    public Course(Long id, String courseNumber, String courseName, String creditHours) {
        this.id = id;
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", creditHours='" + creditHours + '\'' +
                '}';
    }
}
