package com.hashmac.islam360.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.hashmac.islam360.R;
import com.hashmac.islam360.activity.AzkarActivity;
import com.hashmac.islam360.utilities.PrefManager;

import java.util.Random;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNELID = "ShopReminderID";
    public static final String CHANNELNAME = "Shop Reminder";
    PrefManager prefManager;
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        prefManager = new PrefManager(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.enableVibration(prefManager.getVibration());
        if (!prefManager.getSound())
            channel.setImportance(NotificationManager.IMPORTANCE_LOW);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(int p, String title, String description) {
        Intent intent = new Intent(getApplicationContext(), AzkarActivity.class);
        intent.putExtra("id", p);
        intent.putExtra("mode", "zikerday");
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent,PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getApplicationContext(), CHANNELID)
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(pendingIntent)
                .setColor(getResources().getColor(R.color.primary))
                .setSmallIcon(R.drawable.ic_quran);


        if (!prefManager.getSound())
            builder.setSilent(true);

        if (!prefManager.getVibration())
            builder.setVibrate(null);
        return builder;
    }

    private int random() {
        return new Random().nextInt(266);
    }
}
