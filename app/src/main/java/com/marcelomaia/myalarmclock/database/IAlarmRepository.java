package com.marcelomaia.myalarmclock.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IAlarmRepository {

    public LiveData<List<Alarm>> getAll();

    public void insert(Alarm... alarms);
}
