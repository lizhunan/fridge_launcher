package com.bysj.lizhunan.core;

import android.os.Message;
import android.util.Log;

import com.bysj.lizhunan.base.BaseHandler;
import com.bysj.lizhunan.base.LauncherApplication;
import com.bysj.lizhunan.base.What;
import com.bysj.lizhunan.bean.Used;
import com.bysj.lizhunan.core.MemoryMonitor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 循环绘制折线图任务
 */
public class LooperTask extends TimerTask {

    private static LooperTask INSTANCE;
    private BaseHandler handler;
    private int what;
    private String pkgName;

    public static LooperTask getINSTANCE(BaseHandler handler) {
        if (INSTANCE == null) {
            INSTANCE = new LooperTask(handler);
        }
        return INSTANCE;
    }

    public LooperTask(BaseHandler handler) {
        this.handler = handler;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public void setSomething(String pkgName) {
        this.pkgName = pkgName;
    }

    @Override
    public void run() {
        Message message = handler.obtainMessage();
        Used used = new Used();
        switch (what) {
            case What.USED_DATA_CHANGE:
                used.setCpuPer(CpuMonitor.getINSTANCE().getThisCpu());
                used.setMemoryPer( MemoryMonitor.getMemoryPercent());
                used.setMemoryUsed((int) (MemoryMonitor.getTotalSize() - MemoryMonitor.getAvailableMemory()/1024));
                used.setCurrNet((int) NetMonitor.getINSTANCE().getNetSpeedBytes());
                message.obj = used;
                message.what = What.USED_DATA_CHANGE;
                handler.sendMessage(message);
                break;
            case What.PROCESS_USED_DATA_CHANGE:
                Log.d("LooperTask:", "LINE_CHART_CHANGE_MEMORY"+pkgName);
                used.setMemoryPer( MemoryMonitor.getMemoryPercent());
                used.setCurrMemory(MemoryMonitor.getAppMemoryPercent(pkgName));
                used.setMemoryUsed((int) MemoryMonitor.getAppPss(pkgName));
                message.obj = used;
                message.what = What.PROCESS_USED_DATA_CHANGE;
                handler.sendMessage(message);
                break;
        }
    }
}
