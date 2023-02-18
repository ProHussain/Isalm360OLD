package com.hashmac.islam360.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.hashmac.islam360.R;


public class NotificationHelperAdhan extends ContextWrapper {
    public static final String CHANNELID = "QuranID";
    public static final String CHANNELNAME = "Al-Quran";

    private NotificationManager mManager;

    public NotificationHelperAdhan(Context base, Uri sound) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(sound);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel(Uri sound) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELNAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setSound(sound, attributes);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String description, Uri sound) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNELID)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(false)
                .setSound(sound, AudioManager.STREAM_MUSIC)
                .setColor(getResources().getColor(android.R.color.white))
                .setSmallIcon(R.drawable.ic_azkar);
    }

}
