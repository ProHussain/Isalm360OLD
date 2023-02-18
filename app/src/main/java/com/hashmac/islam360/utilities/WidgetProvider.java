package com.hashmac.islam360.utilities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.hashmac.islam360.model.Azkar;
import com.hashmac.islam360.R;
import com.hashmac.islam360.activity.AzkarActivity;
import com.hashmac.islam360.activity.MainActivity;


public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        try (DataBaseHandler db = new DataBaseHandler(context)) {
            // Get all ids
            ComponentName thisWidget = new ComponentName(context,
                    WidgetProvider.class);
            int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            for (int widgetId : allWidgetIds) {
                // create some random data
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                int id = preferences.getInt("id", MainActivity.QOD);
                Azkar cn = db.getAzkar(id);

                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.widget_layout);

                // Set the text
                remoteViews.setTextViewText(
                        R.id.update,
                        String.valueOf(cn.getAzkar() + "\n - " + cn.getName()
                                + " -"));

                // Register an onClickListener
                Intent intent = new Intent(context, AzkarActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("mode", "zikerday");
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }
}