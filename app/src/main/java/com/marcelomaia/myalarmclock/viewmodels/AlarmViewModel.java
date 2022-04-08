package com.marcelomaia.myalarmclock.viewmodels;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.marcelomaia.myalarmclock.database.Alarm;
import com.marcelomaia.myalarmclock.database.AlarmRepository;
import com.marcelomaia.myalarmclock.database.IAlarmRepository;

import java.util.Calendar;

public class AlarmViewModel extends AndroidViewModel {
    String TAG = "MACAddAlarmViewModel";

    public AlarmViewModel(@NonNull Application application) {
        super(application);
    }

    public void addAlarm(Context context, String name, int[] t) {
        addAlarmManager(context, t);
        saveAlarm(name, t[0] + ":" + t[1], t[2] + "/" + t[3] + "/" + t[4], true);
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

    public void addAlarmManager(Context context, int[] t) {
        Intent intent = new Intent("TRIGGERED_ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, t[0]);
        c.set(Calendar.MINUTE, t[1]);
        c.set(Calendar.DAY_OF_MONTH, t[2]);
        c.set(Calendar.MONTH, t[3]);
        c.set(Calendar.YEAR, t[4]);
        c.set(Calendar.SECOND, 0);

        Log.i(TAG, "time: " + t[0] + ":" + t[1] + " " + t[2] + "/" + t[2] + "/"+ t[4]);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        AlarmManager.AlarmClockInfo alarmClockInfo;
        alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent);
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
    }
}
