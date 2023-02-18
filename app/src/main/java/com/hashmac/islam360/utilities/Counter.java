package com.hashmac.islam360.utilities;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Counter {

    private static CountDownTimer counter;

    public static CountDownTimer getInstance(TextView t, Long val) {
        stop();
        counter = init(t, val);
        return counter;
    }

    private static CountDownTimer init(TextView textView, long to) {

        CountDownTimer countDownTimer = new CountDownTimer(to, 1000) {
            @Override
            public void onTick(long l) {
               @SuppressLint("SimpleDateFormat")
               SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
               format.setTimeZone(TimeZone.getTimeZone("UTC"));
               textView.setText(format.format(new Date(l)));
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer.start();

        return countDownTimer;
    }

    public static void stop() {
        if (counter != null) {
            counter.cancel();
            counter = null;
        }
    }

}
