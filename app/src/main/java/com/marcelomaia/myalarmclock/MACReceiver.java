package com.marcelomaia.myalarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MACReceiver extends BroadcastReceiver {
    String TAG = "MACReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("TRIGGERED_ALARM")) {
            Log.i(TAG, "onReceive: TRIGGERED_ALARM");
            Toast.makeText(context, "TRIGGERED_ALARM", Toast.LENGTH_SHORT).show();
        }
    }
}
