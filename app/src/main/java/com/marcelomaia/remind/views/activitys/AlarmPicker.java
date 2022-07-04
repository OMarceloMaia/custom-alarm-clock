package com.marcelomaia.remind.views.activitys;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.marcelomaia.remind.R;
import com.marcelomaia.remind.viewmodels.AlarmViewModel;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AlarmPicker extends AppCompatActivity {
    String TAG = "AlarmPicker";

    TextView name, time, date;
    Switch monthlyRecurrence;
    EditText recurrence;
    Button confirmAdd;
    Context context = this;
    AlarmViewModel alarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_picker);

        int[] t = new int[5];
        name =  findViewById(R.id.tvName);
        time = findViewById(R.id.tvTime);
        date = findViewById(R.id.tvDate);
        monthlyRecurrence = findViewById(R.id.swMonthlyRecurrence);
        recurrence = findViewById(R.id.etRecurrence);
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
                        t[0] = Integer.parseInt(new DecimalFormat("00").format(hourOfDay));
                        t[1] = Integer.parseInt(new DecimalFormat("00").format(minute));

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
                        t[2] = Integer.parseInt(new DecimalFormat("00").format(day));
                        t[3] = Integer.parseInt(new DecimalFormat("00").format(month));
                        t[4] = Integer.parseInt(new DecimalFormat("0000").format(year));

                        date.setText(t[2] + "/" + t[3] + "/" + t[4]);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmViewModel = new ViewModelProvider(AlarmPicker.this).get(AlarmViewModel.class);
                alarmViewModel.addAlarm(AlarmPicker.this, "Test", t, monthlyRecurrence.isChecked(), recurrence.getText().toString());
                finish();
            }
        });
    }
}