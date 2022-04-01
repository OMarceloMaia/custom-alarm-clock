package com.marcelomaia.myalarmclock.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.marcelomaia.myalarmclock.database.Alarm;
import com.marcelomaia.myalarmclock.database.AlarmRepository;
import com.marcelomaia.myalarmclock.database.IAlarmRepository;

public class AddAlarmViewModel extends AndroidViewModel {

    public AddAlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveAlarm(String name, String time, String date, boolean active) {

        IAlarmRepository repository = AlarmRepository.getInstance(getApplication().getApplicationContext());

        Alarm alarm = new Alarm();
        alarm.name = name;
        alarm.time = time;
        alarm.date = date;
        alarm.active = active;
        repository.insert(alarm);
    }
}
