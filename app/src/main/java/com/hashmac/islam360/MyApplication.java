package com.hashmac.islam360;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static final String ONESIGNAL_APP_ID = "00f68d22-f82a-4bb4-9490-a65918a538cb";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}