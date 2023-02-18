package com.hashmac.islam360.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasbeeh_table")
public class TasbeehModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String tasbeehName;

    public TasbeehModel(int id, String tasbeehName) {
        this.id = id;
        this.tasbeehName = tasbeehName;
    }

    @Ignore
    public TasbeehModel(String tasbeehName) {
        this.tasbeehName = tasbeehName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTasbeehName() {
        return tasbeehName;
    }

    public void setTasbeehName(String tasbeehName) {
        this.tasbeehName = tasbeehName;
    }
}
