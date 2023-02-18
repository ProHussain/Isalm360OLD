package com.hashmac.islam360.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.hashmac.islam360.R;
import com.hashmac.islam360.utilities.PrefManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InfiniteService extends Service {

    public static final int TYPE_ADHAN = 1;
    public static final int TYPE_REMINDER = 2;
    private Intent mIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //mIntent = intent;

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);


        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.d("SCHEDULE", "run: running...");
                checkPrayerTimes();
            }
        }, 0, 1, TimeUnit.MINUTES);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_REDELIVER_INTENT;
    }

    private void checkPrayerTimes() {
        PrefManager prefManager = new PrefManager(getApplicationContext());

        long NOW = System.currentTimeMillis();

        int salat = 0;
        int type = 0;

        String fajr = prefManager.getFajr();
        String sunrise = prefManager.getSunrise();
        String dhuhr = prefManager.getDhuhr();
        String asr = prefManager.getAsr();
        String maghrib = prefManager.getMaghrib();
        String isha = prefManager.getIsha();

        if (approximatelyAdhanTime(fajr, NOW)) {
            salat = 1;
            type = TYPE_ADHAN;
        } else if (approximatelyAdhanTime(dhuhr, NOW)) {
            salat = 2;
            type = TYPE_ADHAN;
        } else if (approximatelyAdhanTime(asr, NOW)) {
            salat = 3;
            type = TYPE_ADHAN;
        } else if (approximatelyAdhanTime(maghrib, NOW)) {
            salat = 4;
            type = TYPE_ADHAN;
        } else if (approximatelyAdhanTime(isha, NOW)) {
            salat = 5;
            type = TYPE_ADHAN;
        } else {
            if (approximatelyReminderTime(fajr, NOW)) {
                salat = 1;
                type = TYPE_REMINDER;
            } else if (approximatelyReminderTime(dhuhr, NOW)) {
                salat = 2;
                type = TYPE_REMINDER;
            } else if (approximatelyReminderTime(asr, NOW)) {
                salat = 3;
                type = TYPE_REMINDER;
            } else if (approximatelyReminderTime(maghrib, NOW)) {
                salat = 4;
                type = TYPE_REMINDER;
            } else if (approximatelyReminderTime(isha, NOW)) {
                salat = 5;
                type = TYPE_REMINDER;
            } else {
                return;
            }
        }

        Intent reciever = new Intent(getApplicationContext(), InfiniteReceiver.class);
        reciever.putExtra("received_salat", salat);
        reciever.putExtra("received_type", type);
        sendBroadcast(reciever);

    }

    private boolean approximatelyAdhanTime(String salat, long now) {
        long DELAY = 30 * 1000;
        return now > (prayerToMillis(salat) - DELAY) && now < (prayerToMillis(salat) + DELAY);
    }

    private boolean approximatelyReminderTime(String salat, long now) {
        long DELAY = 30 * 1000;
        long OFFSET = 10 * 60 * 1000;
        return now > (prayerToMillis(salat) - OFFSET - DELAY) && now < (prayerToMillis(salat) - OFFSET + DELAY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mIntent != null) {
            updateReminder(mIntent);
        }
        Log.d("SCHEDULE", "Destroy: ");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("SCHEDULE", "Task removed: ");
        mIntent = rootIntent;
        updateReminder(rootIntent);
    }

    private void updateReminder(Intent intent) {

        stopSelf();
        startService(intent);

    }

    private void adhan(Context context, String salat, String before) {
        NotificationHelperAdhan notificationHelper = new NotificationHelperAdhan(context, Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adhan));
        NotificationCompat.Builder builder = notificationHelper
                .getChannelNotification(context.getString(R.string.adhan), before + salat,
                        Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adhan));

        notificationHelper.getManager().notify(new Random().nextInt(10000), builder.build());
    }

    private void reminder(Context context, String salat, String before) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper
                .getChannelNotification(0, "Adhan Reminder", before + salat);

        notificationHelper.getManager().notify(new Random().nextInt(10000), builder.build());
    }


    private int getHours(String prayerTime) {
        String result = prayerTime.substring(0, prayerTime.indexOf(":"));
        Log.d("TAG", "getHours: " + result);
        return Integer.parseInt(result);
    }

    private int getMinutes(String prayerTime) {
        String result = prayerTime.substring(prayerTime.indexOf(":") + 1);
        Log.d("TAG", "getMinutes: " + result);
        return Integer.parseInt(result);
    }

    private long prayerToMillis(String prayerTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, getHours(prayerTime));
        calendar.set(Calendar.MINUTE, getMinutes(prayerTime));
        calendar.set(Calendar.SECOND, 0);
        Log.d("TAG", "prayerToMillis: " + calendar.getTime().toString() + " " + new Date());
        return calendar.getTimeInMillis();
    }

    private long prayerToMillisNextDay(String prayerTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, getHours(prayerTime));
        calendar.set(Calendar.MINUTE, getMinutes(prayerTime));
        calendar.set(Calendar.SECOND, 0);
        Log.d("TAG", "prayerToMillis: " + calendar.getTime().toString());
        return calendar.getTimeInMillis();
    }
}
