package com.hashmac.islam360.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.hashmac.islam360.service.InfiniteReceiver;
import com.hashmac.islam360.service.NewDayReciever;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Utils {

    private static AlarmManager mAlarmManger;
    private Context context;

    public Utils(Context context) {
        this.context = context;
        mAlarmManger = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public static Utils getInstance(Context context) {
        return new Utils(context);
    }


    public static void setAlarm(Context context) {

        AlarmManager mAlarmManger = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(context, NewDayReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1011, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        long now = System.currentTimeMillis();
        long midnight = calendar.getTimeInMillis();

        if (now < midnight) {

            Log.d("TAG", "midnight" + calendar.getTime().toString());
            Log.d("TAG", "now" + new Date(System.currentTimeMillis()).toString());

            mAlarmManger.setRepeating(AlarmManager.RTC, midnight,
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {


            Log.d("TAG", "onReceive: midnight fff");

        }

    }

    public void setPrayerAlarm(int id, long when, String salat, boolean isAdhan) {

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(context, InfiniteReceiver.class);

        InfiniteReceiver.startWakefulService(context, intent);

        intent.putExtra("salat", salat);
        intent.putExtra("mode", isAdhan);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManger.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManger.setExact(AlarmManager.RTC_WAKEUP, when, pendingIntent);
        } else {
            mAlarmManger.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
        }*/

        mAlarmManger.setRepeating(AlarmManager.RTC_WAKEUP, when, AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d("TAG", "STARTED ALARM " + new Date(when) + " " + salat);
    }

    public void cancelPrayerAlarm(int id) {

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(context, InfiniteReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        mAlarmManger.cancel(pendingIntent);

        Log.d("TAG", "CANCELED ALARM");

    }

    public void resetAdhanAlarm(int id, long when, long next, String salat, boolean isAdhan) {
        if (when < System.currentTimeMillis()) {
            setPrayerAlarm(id, next, salat, isAdhan);
            return;
        }
        //cancelPrayerAlarm(id);
        setPrayerAlarm(id, when, salat, isAdhan);
    }


    private int generateId() {
        Random first = new Random();
        Random second = new Random();
        int result = first.nextInt(1000) * second.nextInt(1000);
        return result;
    }
}
