package com.bysj.lizhunan.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.LauncherApplication;

/**
 * 窗口小部件
 */
public class ShowCtrlAppsWidget extends AppWidgetProvider {

    public static final String BT_REFRESH_ACTION = "com.skywang.test.BT_REFRESH_ACTION";
    public static final String COLLECTION_VIEW_ACTION = "com.skywang.test.COLLECTION_VIEW_ACTION";
    public static final String COLLECTION_VIEW_EXTRA = "com.skywang.test.COLLECTION_VIEW_EXTRA";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.show_ctrl_apps_widget);
        Intent serviceIntent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.app_gv,serviceIntent );

        Intent gridIntent = new Intent();
        gridIntent.setAction(COLLECTION_VIEW_ACTION);
        gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置intent模板
        views.setPendingIntentTemplate(R.id.app_gv, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if (action.equals(COLLECTION_VIEW_ACTION)) {
            // 接受“gridview”的点击事件的广播
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(COLLECTION_VIEW_EXTRA, 0);

            //启动第三方APP
            PackageManager packageManager = LauncherApplication.getContext().getPackageManager();
            Intent startIntent=new Intent();
            startIntent = packageManager.getLaunchIntentForPackage(GridWidgetService.GridWidgetViewFactory.apps.get(viewIndex).getPackageName());
            if(startIntent == null){
                Toast.makeText(context, context.getString(R.string.not_found_app), Toast.LENGTH_SHORT).show();
            }else{
                context.startActivity(startIntent);
            }
            // Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        } else if (action.equals(BT_REFRESH_ACTION)) {
            // 接受“bt_refresh”的点击事件的广播
            Toast.makeText(context, "Click Button", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}

