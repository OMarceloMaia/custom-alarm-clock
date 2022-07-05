package com.marcelomaia.remind.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IAlarmRepository {

    public LiveData<List<Alarm>> getAll();

    public long insert(Alarm alarm);
}
