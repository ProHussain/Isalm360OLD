package com.hashmac.islam360.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.hashmac.islam360.utilities.PrefManager;

public class ReminderReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        PrefManager manager = new PrefManager(context.getApplicationContext());
        if (manager.getPushNotification()) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            int notificationID = intent.getIntExtra("notificationID", 0);
            int position = intent.getIntExtra("position", 0);
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder builder = notificationHelper
                    .getChannelNotification(position, title, description);
            notificationHelper.getManager().notify(notificationID, builder.build());
        }
    }
}
