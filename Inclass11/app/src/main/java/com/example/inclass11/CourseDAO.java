package com.example.inclass11;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Query("SELECT * FROM Course")
    List<Course> getAll();

    @Query("SELECT * FROM Course WHERE id IN (:courseIds)")
    List<Course> loadAllByIds(int[] courseIds);

    @Insert
    void insertAll(Course... courses);

    @Delete
    void delete(Course course);

    @Update
    void update(Course course);

}
