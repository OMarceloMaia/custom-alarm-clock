package com.marcelomaia.remind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.marcelomaia.remind.service.AlarmService;

public class MACReceiver extends BroadcastReceiver {
    String TAG = "MACReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("TRIGGERED_ALARM")) {
            Log.i(TAG, "onReceive: TRIGGERED_ALARM");
            Toast.makeText(context, "TRIGGERED_ALARM", Toast.LENGTH_SHORT).show();

            startAlarmService(context, intent);
        }
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}