package com.marcelomaia.remind.viewmodels;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.marcelomaia.remind.data.Alarm;
import com.marcelomaia.remind.data.AlarmRepository;
import com.marcelomaia.remind.data.IAlarmRepository;
import com.marcelomaia.remind.usecases.RecurrenceMonthly;
import com.marcelomaia.remind.usecases.SingleAlarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    String TAG = "AlarmViewModel";

    public AlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public void addAlarm(Context context, String name, int[] t, boolean monthlyRecurrence, String recurrence) {
        addAlarmManager(context, t, monthlyRecurrence, recurrence);
        saveAlarm(name, t[0] + ":" + t[1], t[2] + "/" + t[3] + "/" + t[4], monthlyRecurrence, recurrence, true);
    }

    public void saveAlarm(String name, String time, String date, boolean monthlyRecurrence, String recurrence, boolean active) {
        IAlarmRepository repository = AlarmRepository.getInstance(getApplication().getApplicationContext());

        Alarm alarm = new Alarm();
        alarm.name = name;
        alarm.time = time;
        alarm.date = date;
        alarm.monthlyRecurrence = monthlyRecurrence;
        alarm.recurrence = recurrence;
        alarm.active = active;
        repository.insert(alarm);
    }

    public void addAlarmManager(Context context, int[] t, boolean monthlyRecurrence, String recurrence) {
        Intent intent = new Intent("TRIGGERED_ALARM");

        if (monthlyRecurrence) {
            RecurrenceMonthly.AddAlarm(context, t, intent, recurrence);
        } else {
            SingleAlarm.AddAlarm(context, t, intent);
        }
    }
}
