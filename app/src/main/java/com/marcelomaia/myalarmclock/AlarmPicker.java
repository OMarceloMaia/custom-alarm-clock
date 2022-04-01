package com.marcelomaia.myalarmclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.marcelomaia.myalarmclock.database.Alarm;
import com.marcelomaia.myalarmclock.database.AlarmDao;
import com.marcelomaia.myalarmclock.database.AlarmRepository;
import com.marcelomaia.myalarmclock.database.AppDatabase;
import com.marcelomaia.myalarmclock.database.IAlarmRepository;
import com.marcelomaia.myalarmclock.viewmodels.AddAlarmViewModel;
import com.marcelomaia.myalarmclock.viewmodels.MainViewModel;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

public class AlarmPicker extends AppCompatActivity {
    String TAG = "MACAlarmPicker";

    TextView name, time, date;
    Button confirmAdd;
    Context context = this;
    AddAlarmViewModel addAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);

        String[] t = new String[5];
        name =  findViewById(R.id.tvName);
        time = findViewById(R.id.tvTime);
        date = findViewById(R.id.tvDate);
        confirmAdd = findViewById(R.id.btConfirmAdd);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        t[0] = new DecimalFormat("00").format(hourOfDay);
                        t[1] = new DecimalFormat("00").format(minute);

                        time.setText(t[0] + ":" + t[1]);
                    }
                }, hourOfDay, minute, android.text.format.DateFormat.is24HourFormat(context));
                timePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        t[2] = new DecimalFormat("00").format(day);
                        t[3] = new DecimalFormat("00").format(month);
                        t[4] = new DecimalFormat("0000").format(year);

                        date.setText(t[2] + "/" + t[3] + "/" + t[4]);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("TRIGGERED_ALARM");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPicker.this, 0, intent, 0);

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
                c.set(Calendar.MINUTE, Integer.parseInt(t[1]));
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(t[2]));
                c.set(Calendar.MONTH, Integer.parseInt(t[3]));
                c.set(Calendar.YEAR, Integer.parseInt(t[4]));
                c.set(Calendar.SECOND, 0);

                Log.i(TAG, "time: " + t[0] + ":" + t[1] + " " + t[2] + "/" + t[2] + "/"+ t[4]);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                AlarmManager.AlarmClockInfo alarmClockInfo;
                alarmClockInfo = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pendingIntent );
                alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);

//                Intent intent = new Intent("TRIGGERED_ALARM");
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPicker.this, 0, intent, 0);
//
//                Calendar calendar = Calendar.getInstance();
//                    calendar.setTimeInMillis(System.currentTimeMillis());
//                    calendar.add(Calendar.SECOND, 10);
//
//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                addAlarmViewModel = new ViewModelProvider(AlarmPicker.this).get(AddAlarmViewModel.class);
                addAlarmViewModel.saveAlarm("Teste", t[0] + ":" + t[1], t[2] + "/" + t[3] + "/" + t[4], true);

                finish();
            }
        });
    }
}