package com.marcelomaia.remind.data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository implements IAlarmRepository {
    String TAG = "MAC" + getClass().getName();
    private static  IAlarmRepository INSTANCE;
    private AppDatabase mDatabase;

    private  AlarmRepository(Context context) {
        mDatabase = AppDatabase.getInstance(context);
    }

    public static IAlarmRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AlarmRepository(context);
        }
        return INSTANCE;
    }

    public LiveData<List<Alarm>> getAll() {
        Log.i(TAG, "getAll: called");
        return mDatabase.alarmDao().getAll();
    }

    public void insert(Alarm... alarms) {
        Log.i(TAG, "insert: called");
        mDatabase.alarmDao().insertAll(alarms);
    }
}
