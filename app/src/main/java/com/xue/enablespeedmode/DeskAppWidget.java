package com.xue.enablespeedmode;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class DeskAppWidget extends AppWidgetProvider {
    //定义一个action，这个action要在AndroidMainfest中去定义，不然识别不到，名字是自定义的
    private static final String CLICK_ACTION = "com.xue.enablespeedmode.CLICK";
    private static final String update_ACTION = "com.xue.enablespeedmode.updateStatus";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //当我们点击桌面上的widget按钮（这个按钮我们在onUpdate中已经为它设置了监听），widget就会发送广播
        //这个广播我们也在onUpdate中为它设置好了意图，设置了action，在这里我们接收到对应的action并做相应处理
        if (intent.getAction().equals(CLICK_ACTION)) {
            boolean currentStatus = Settings.System.getInt(context.getContentResolver(), "speed_mode", 0) == 1;
            Settings.System.putInt(context.getContentResolver(), "speed_mode", currentStatus ? 0 : 1);
            //因为点击按钮后要对布局中的文本进行更新，所以需要创建一个远程view
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desk_app_widget);
            //为对应的TextView设置文本
            remoteViews.setTextViewText(R.id.appwidget_btnEnable, currentStatus ? "极致模式已关闭" : "极致模式已启用");
            remoteViews.setTextColor(R.id.appwidget_btnEnable, currentStatus ? context.getResources().getColor(R.color.blue) : context.getResources().getColor(R.color.teal_200));
            //更新widget
            appWidgetManager.updateAppWidget(new ComponentName(context, DeskAppWidget.class), remoteViews);
        } else if (intent.getAction().equals(update_ACTION)) {
            boolean currentStatus = Settings.System.getInt(context.getContentResolver(), "speed_mode", 0) != 1;
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desk_app_widget);
            //为对应的TextView设置文本
            remoteViews.setTextViewText(R.id.appwidget_btnEnable, currentStatus ? "极致模式已关闭" : "极致模式已启用");
            remoteViews.setTextColor(R.id.appwidget_btnEnable, currentStatus ? context.getResources().getColor(R.color.blue) : context.getResources().getColor(R.color.teal_200));
            //更新widget
            appWidgetManager.updateAppWidget(new ComponentName(context, DeskAppWidget.class), remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            boolean currentStatus = Settings.System.getInt(context.getContentResolver(), "speed_mode", 0) == 1;
            //创建一个远程view，绑定我们要操控的widget布局文件
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.desk_app_widget);
            remoteViews.setTextViewText(R.id.appwidget_btnEnable, currentStatus ? "极致模式已关闭" : "极致模式已启用");
            remoteViews.setTextColor(R.id.appwidget_btnEnable, currentStatus ? context.getResources().getColor(R.color.blue) : context.getResources().getColor(R.color.teal_200));
            Intent intentClick = new Intent();
            //这个必须要设置，不然点击效果会无效
            intentClick.setClass(context, DeskAppWidget.class);
            intentClick.setAction(CLICK_ACTION);
            //PendingIntent表示的是一种即将发生的意图，区别于Intent它不是立即会发生的
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
            //为布局文件中的按钮设置点击监听
            remoteViews.setOnClickPendingIntent(R.id.appwidget_btnEnable, pendingIntent);
            //告诉AppWidgetManager对当前应用程序小部件执行更新
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}