package com.bysj.lizhunan.core;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.bean.App;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存监控类
 */
public class MemoryMonitor {

    private static MemoryMonitor INSTANCE;

    public static MemoryMonitor getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MemoryMonitor();
        }
        return INSTANCE;
    }

    /**
     * 获取某个进程的Pss
     *
     * @param pkgName app的包名名
     * @return Pss实际使用的物理内存 ，-1为错误
     */
    public static long getAppPss(String pkgName) {
        Log.d("MemoryMonitor","getAppPss:"+pkgName);
        ActivityManager activityManager = (ActivityManager) LauncherApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses();
        if (list != null) {
            for (AndroidAppProcess androidAppProcess : list) {
                if (androidAppProcess.getPackageName().equals(pkgName)) {
                    Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{androidAppProcess.pid});
                    Debug.MemoryInfo memoryInfo = memoryInfos[0];
                    return memoryInfo.getTotalPss();
                }
            }
        }
        return -1;
    }

    /**
     * 获取所有app的Pss
     *
     * @return 所有进程Pss实际使用的物理内存，-1为错误
     */
    public static long getAppTotalPss(List<App> apps) {
        long total = -1;
        ActivityManager activityMgr = (ActivityManager) LauncherApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses();
        if (list != null) {
            total = 0;
            for (int i = 0;i<list.size();i++) {
                for (int j = 0; j < apps.size(); j++) {
                    if(list.get(i).name.equals(apps.get(j).getProcessName())){
                        Debug.MemoryInfo[] memoryInfos = activityMgr.getProcessMemoryInfo(new int[]{list.get(i).pid});
                        Debug.MemoryInfo memoryInfo = memoryInfos[0];
                        total = total+memoryInfo.getTotalPss();
                    }
                }
            }
            return total;
        }
        return total;
    }

    /**
     * 获取内存百分比
     *
     * @return
     */
    public static int getMemoryPercent() {
        String dir = "/proc/meminfo"; // linux下系统目录
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine
                    .indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll(
                    "\\D+", ""));
            long availableSize = getAvailableMemory() / 1024;
            Log.d("MemoryMonitor","totalMemorySize:"+totalMemorySize+",availableSize:"+availableSize);
            return (int) ((totalMemorySize - availableSize)
                    / (float) totalMemorySize * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取单个进程内存占用率
     *
     * @return
     */
    public static int getAppMemoryPercent(String processName){
        String dir = "/proc/meminfo"; // linux下系统目录
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine
                    .indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll(
                    "\\D+", ""));
            long useMemorySize = getAppPss(processName);
            Log.d("MemoryMonitor","totalMemorySize:"+totalMemorySize+",useMemorySize:"+useMemorySize);
            return (int) (useMemorySize / (float) totalMemorySize * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取可用内存
     *
     * @return long
     */
    public static long getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) LauncherApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * 清理内存
     */
    public static void cleanMemory() {
        ActivityManager activityManger = (ActivityManager) LauncherApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses();
        if (list != null) {
            for (AndroidAppProcess processInfo : list) {
                int myPid = android.os.Process.myPid();
                if (myPid == processInfo.pid) {
                    continue;
                }
                if (!processInfo.foreground) {
                    activityManger.killBackgroundProcesses(processInfo.getPackageName());
                }
            }
        }
    }
}
