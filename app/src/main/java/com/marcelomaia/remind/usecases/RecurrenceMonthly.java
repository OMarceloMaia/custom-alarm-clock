package com.marcelomaia.remind.usecases;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecurrenceMonthly {
    private static final String TAG = "RecurrenceMonthly";

    public static void AddAlarm(Context context, int[] t, Intent intent, String recurrence) {
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

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    (int) c.getTimeInMillis(), intent, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

            // TODO: 04/07/2022 Fix the repeating
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY * 30, pendingIntent);

            AlarmManager.AlarmClockInfo alarmClockInfo;
            alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
        }
    }

    public static List<Integer> getDays(String recurrence) {
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
