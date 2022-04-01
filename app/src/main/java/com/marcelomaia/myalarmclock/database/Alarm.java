package com.marcelomaia.myalarmclock.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {
    @PrimaryKey (autoGenerate = true)
    public int aid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "active")
    public boolean active;
}
