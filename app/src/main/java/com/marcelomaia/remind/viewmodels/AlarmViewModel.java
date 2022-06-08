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
            AddAlarmWithRecurrence(context, t, intent, recurrence);
        } else {
            AddAlarmWithoutRecurrence(context, t, intent);
        }
    }

    public void AddAlarmWithoutRecurrence(Context context, int[] t, Intent intent) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, t[0]);
        c.set(Calendar.MINUTE, t[1]);
        c.set(Calendar.DAY_OF_MONTH, t[2]);
        c.set(Calendar.MONTH, t[3]);
        c.set(Calendar.YEAR, t[4]);
        c.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) c.getTimeInMillis(), intent, 0);

        Log.i(TAG, "Time: " + c.getTime());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        AlarmManager.AlarmClockInfo alarmClockInfo;
        alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent);
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
    }

    public void AddAlarmWithRecurrence(Context context, int[] t, Intent intent, String recurrence) {
        List<Integer> days = getDays(recurrence);
        Log.i(TAG, "days: " + days);

        Calendar c = Calendar.getInstance();
        for (int day : days) {

            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, t[0]);
            c.set(Calendar.MINUTE, t[1]);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.MONTH, t[3]);
            c.set(Calendar.YEAR, t[4]);
            c.set(Calendar.SECOND, 0);

            Log.i(TAG, "Time: " + c.getTime());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) c.getTimeInMillis(), intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 30, pendingIntent);

            AlarmManager.AlarmClockInfo alarmClockInfo;
            alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        }
    }

    public List<Integer> getDays(String recurrence) {
        String[] strings = recurrence.split("/");
        List<Integer> days = new ArrayList<Integer>();

        for (String string : strings) {
            int indexLine = string.indexOf("-");

            if(indexLine != -1) {
                int start = (Integer.parseInt(string.substring(0, indexLine)));
                int end = (Integer.parseInt(string.substring(indexLine + 1, string.length())));

                for (int i = start; i <= end; i++) {
                    days.add(i);
                }
            } else {
                days.add(Integer.parseInt(string));
            }
        }
        return days;
    }
}
