package com.marcelomaia.remind.usecases;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class SingleAlarm {
    private static final String TAG = "SingleAlarm";

    public static void AddAlarm(Context context, int[] t, Intent intent, long alarmId) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, t[0]);
        c.set(Calendar.MINUTE, t[1]);
        c.set(Calendar.DAY_OF_MONTH, t[2]);
        c.set(Calendar.MONTH, t[3]);
        c.set(Calendar.YEAR, t[4]);
        c.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) alarmId, intent,
                0);

        Log.i(TAG, "Time: " + c.getTime());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

        AlarmManager.AlarmClockInfo alarmClockInfo;
        alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent);
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
    }
}
