package com.example.inclass11;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Course.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDAO courseDAO();
}
