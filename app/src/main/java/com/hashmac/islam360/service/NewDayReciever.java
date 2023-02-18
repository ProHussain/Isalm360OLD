package com.hashmac.islam360.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewDayReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "onReceive: New DAY!");


        Intent service = new Intent(context, InfiniteService.class);
        context.startService(service);


    }
}
