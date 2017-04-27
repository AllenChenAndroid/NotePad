package com.example.cl.notepad.padface;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.cl.notepad.R;

/**
 * Created by cl on 2017/4/10.
 */

public class NotePadAppWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i=0;i<appWidgetIds.length;i++){
            Intent intent=new Intent(context,NotePadMainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.appwidget);
            remoteViews.setOnClickPendingIntent(R.id.appname,pendingIntent);
            intent=new Intent(context,NotePadNewActivity.class);
            pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            remoteViews.setOnClickPendingIntent(R.id.widgetAddButton,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
        }
    }
}
