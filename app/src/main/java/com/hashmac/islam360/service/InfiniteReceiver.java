package com.hashmac.islam360.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;


import com.hashmac.islam360.R;

import java.util.Random;

public class InfiniteReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
/*
        if (intent != null) {
            int salat = intent.getIntExtra("received_salat", 0);
            int type = intent.getIntExtra("received_type", 0);

            if (salat == 0) {
                return;
            }

            switch (type) {
                case TYPE_ADHAN:
                    adhan(context, getSalat(salat), "{NEW} Now Salat Al-");
                    break;
                case TYPE_REMINDER:
                    reminder(context, getSalat(salat), "{NEW} 10 min left to ");
                    break;
            }

        }*/

        if (intent != null) {

            boolean isAdhan = intent.getBooleanExtra("mode", false);

            String salat = intent.getStringExtra("salat");

            if (isAdhan) {
                adhan(context, salat, "");
            } else {
                reminder(context, salat, "");
            }

        }

    }


    private String getSalat(Context context, String salat) {
        String result = "";
        switch (salat) {
            case "Fajr":
                result = context.getString(R.string.fajr);
                break;
            case "Dhuhr":
                result = context.getString(R.string.dhuhr);
                break;
            case "Asr":
                result = context.getString(R.string.asr);
                break;
            case "Maghrib":
                result = context.getString(R.string.maghrib);
                break;
            case "Isha":
                result = context.getString(R.string.isha);
                break;
        }
        return result;
    }

    private void adhan(Context context, String salat, String before) {
        NotificationHelperAdhan notificationHelper = new NotificationHelperAdhan(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adhan));
        NotificationCompat.Builder builder = notificationHelper
                .getChannelNotification(context.getString(R.string.adhan), before + getSalat(context, salat),
                        Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adhan));

        notificationHelper.getManager().notify(new Random().nextInt(10000), builder.build());
    }

    private void reminder(Context context, String salat, String before) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper
                .getChannelNotification(0, "Adhan Reminder", "");

        notificationHelper.getManager().notify(new Random().nextInt(10000), builder.build());
    }

}
