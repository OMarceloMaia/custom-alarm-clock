package com.marcelomaia.remind.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM Alarm")
    LiveData<List<Alarm>> getAll();

    @Query("SELECT * FROM Alarm WHERE aid IN (:alarmsIds)")
    LiveData<List<Alarm>> loadAllByIds(int[] alarmsIds);

    @Query("SELECT * FROM Alarm WHERE name LIKE :nName LIMIT 1")
    Alarm findByName(String nName);

    @Insert
    void insertAll(Alarm... alarms);

    @Delete
    void delete(Alarm alarm);
}
