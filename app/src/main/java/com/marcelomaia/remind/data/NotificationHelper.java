package com.marcelomaia.remind.data;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.marcelomaia.remind.R;
import com.marcelomaia.remind.views.activitys.RingActivity;
import com.marcelomaia.remind.views.receiver.RemindReceiver;

public class NotificationHelper {
    private static final String ACTION_DISMISS = "ACTION_DISMISS";
    private static final String ACTION_SNOOZE = "ACTION_SNOOZE";
    public static final String CHANNEL_ID = "ALARM_SERVICE_ID";
    private static final String CHANNEL_NAME = "ALARM_SERVICE_CHANNEL";
    private static final int NOTIFICATION_ID = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void showNotification(Context context) {
        initNotificationChannel(context);

        // TODO: 04/07/2022 Implement user permission request and button actions
        // Responsible for showing the RingActivity when the alarm is triggered
        Intent fullScreenIntent = new Intent(context, RingActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Buttons in the notification
        Intent dismissIntent = new Intent(context, RemindReceiver.class);
        dismissIntent.setAction(ACTION_DISMISS);
        dismissIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent dismissPendingIntent =
                PendingIntent.getBroadcast(context, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(context, RemindReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Alarm Tittle")
                .setContentText("Alarm Description")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .addAction(R.drawable.ic_alarm_black_24dp, "DISMISS", dismissPendingIntent)
                .addAction(R.drawable.ic_alarm_black_24dp, "SNOOZE", snoozePendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void initNotificationChannel(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
