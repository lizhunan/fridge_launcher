package com.bysj.lizhunan.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseFragment;
import com.bysj.lizhunan.base.BaseHandler;
import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.presenter.AppsPresenter;
import com.bysj.lizhunan.ui.fragment.IGetData;

import java.util.ArrayList;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridWidgetViewFactory(this,intent);
    }

    public static class GridWidgetViewFactory implements RemoteViewsFactory , IGetData<List<App>> {

        private Context context;
        private int mAppWidgetId;
        public static List<App> apps = new ArrayList<>();
        private PackageManager pm;

        public GridWidgetViewFactory(Context context, Intent intent) {
            this.context = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            pm = LauncherApplication.getContext().getPackageManager();
            List<PackageInfo> allApp = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);//查询所有app

            apps.clear();
            for (int j = 0; j < allApp.size(); j++) {
                apps.add(getAppInfo(allApp.get(j)));
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            apps.clear();
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_grid_item);
            Drawable drawable = apps.get(i).getImage();
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            rv.setImageViewBitmap(R.id.app_iv, bitmapDrawable.getBitmap());
            rv.setTextViewText(R.id.app_name, apps.get(i).getAppName());
            rv.setTextColor(R.id.app_name, Color.BLACK);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(ShowCtrlAppsWidget.COLLECTION_VIEW_EXTRA, i);
            rv.setOnClickFillInIntent(R.id.app_ll, fillInIntent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void showSuccess(List<App> appInfos) {
            apps.clear();
            for (int i = 0; i < appInfos.size(); i++) {
                Log.d("show", appInfos.get(i).getAppName());
            }
            apps.addAll(appInfos);
        }

        @Override
        public void showFail() {

        }

        @Override
        public void showProgress() {

        }

        @Override
        public void hintProgress() {

        }

        @Override
        public void refreshUI() {

        }
        private App getAppInfo(PackageInfo packageInfo) {
            App appInfo = new App();
            appInfo.setAppName((String) packageInfo.applicationInfo.loadLabel(pm));
            appInfo.setPackageName(packageInfo.applicationInfo.packageName);
            appInfo.setImage(packageInfo.applicationInfo.loadIcon(pm));
            appInfo.setProcessName(packageInfo.applicationInfo.processName);
            appInfo.setVersion(packageInfo.versionName);
            return appInfo;
        }
    }
}
