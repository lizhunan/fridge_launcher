package com.bysj.lizhunan.core;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.bean.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取app有关信息类
 */
public class AppsHandler {

    private static AppsHandler INSTANCE;
    private static PackageManager pm;

    public static AppsHandler getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new AppsHandler();
        }
        return INSTANCE;
    }
    public AppsHandler(){
        pm = LauncherApplication.getContext().getPackageManager();
    }

    /**
     * 获取设备所有App详情（不包括已被管控app）
     *
     * @return List<App></>
     */
    public List<App> getAppsInfo(){
        List<App> apps = new ArrayList<>();
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
        for (PackageInfo pkg : packageInfos) {
            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                if (pkg.packageName.equals("com.bysj.lizhunan")) {
                    continue;
                }
                apps.add(getAppInfo(pkg));
            }else if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {//本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
                apps.add(getAppInfo(pkg));
            }
        }
        return apps;
    }

    /**
     * 获取指定的app
     * @param appName
     * @return
     */
    public App getAppInfo(String appName){
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        for (int i = 0;i<=packageInfos.size();i++) {
            if(packageInfos.get(i).applicationInfo.name.equals(appName)){
                return getAppInfo(packageInfos.get(i));
            }
        }
        return null;
    }

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
        appInfo.setVersion(packageInfo.versionName);
        return appInfo;
    }
}
