package com.hashmac.islam360.interfaces;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.hashmac.islam360.model.TasbeehModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insertTasbeeh(TasbeehModel model);

    @Delete
    void deleteTasbeeh(TasbeehModel model);

    @Query("SELECT * FROM tasbeeh_table")
    List<TasbeehModel> getAllTasbeeh();

    @Query("DELETE FROM tasbeeh_table")
    void deleteAllCourses();
}
