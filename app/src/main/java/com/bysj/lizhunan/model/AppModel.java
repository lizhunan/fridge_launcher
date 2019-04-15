package com.bysj.lizhunan.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.bean.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * app信息获取类
 */
public class AppModel {


    private PackageManager pm;

    /**
     * 构造一个appinfo对象
     *
     * @param packageInfo 原始app信息
     * @return Appinfo对象
     */
    private App getAppInfo(PackageInfo packageInfo) {
        App appInfo = new App();
        appInfo.setAppName((String) packageInfo.applicationInfo.loadLabel(pm));
        appInfo.setPackageName(packageInfo.applicationInfo.packageName);
        appInfo.setImage(packageInfo.applicationInfo.loadIcon(pm));
        appInfo.setProcessName(packageInfo.applicationInfo.processName);
        appInfo.setVersion(packageInfo.versionName);
        return appInfo;
    }

    public void data(final int i, final OnGetAppInfo onGetAppInfo, final String appName) {
        final List<App> appInfos = new ArrayList<App>(); // 保存过滤查到的AppInfo
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                onGetAppInfo.onLoading();
                pm = LauncherApplication.getContext().getPackageManager();
                List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
                Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
                switch (i) {
                    case 0:
                        appInfos.clear();
                        for (PackageInfo pkg : packageInfos) {
                            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                                appInfos.add(getAppInfo(pkg));
                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                    case 1:
                        appInfos.clear();
                        for (PackageInfo pkg : packageInfos) {
                            //非系统程序
                            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                                if (pkg.packageName.equals("com.bysj.lizhunan")) {
                                    continue;
                                }
                                appInfos.add(getAppInfo(pkg));
                            } else if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {//本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
                                appInfos.add(getAppInfo(pkg));
                            } else {

                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                    case 2:
                        List<PackageInfo> hiddenApp = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);//查询所有app
                        appInfos.clear();
                        for (int j = 0; j < hiddenApp.size(); j++) {
                            if (LauncherApplication.mDPM.isApplicationHidden(LauncherApplication.mComponentName, hiddenApp.get(j).packageName)) {
                                appInfos.add(getAppInfo(hiddenApp.get(j)));
                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                    case 3:
                        if (!appName.equals("")) {
                            appInfos.clear();
                            for (PackageInfo pkg : packageInfos) {
                                if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                                    if (pkg.applicationInfo.loadLabel(pm).equals(appName)) {
                                        appInfos.add(getAppInfo(pkg));
                                        break;
                                    }
                                } else if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {//本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
                                    if (pkg.applicationInfo.loadLabel(pm).equals(appName)) {
                                        appInfos.add(getAppInfo(pkg));
                                        break;
                                    }
                                } else {

                                }
                            }
                            onGetAppInfo.onLoaded();
                            onGetAppInfo.onSuccess(appInfos);
                        }
                        break;
                }
            }
        }).start();
    }
}
