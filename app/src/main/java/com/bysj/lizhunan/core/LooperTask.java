package com.bysj.lizhunan.core;

import android.os.Message;
import android.util.Log;

import com.bysj.lizhunan.base.BaseHandler;
import com.bysj.lizhunan.base.What;
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
        Log.d("LooperTask:", "run");
        Message message = handler.obtainMessage();
        switch (what) {
            case What.LINE_CHART_CHANGE:
                int i1 = MemoryMonitor.getMemoryPercent();
                message.obj = i1;
                message.what = What.LINE_CHART_CHANGE;
                handler.sendMessage(message);
                break;
            case What.LINE_CHART_CHANGE_MEMORY:
                Log.d("LooperTask:", "LINE_CHART_CHANGE_MEMORY");
                int i2 = MemoryMonitor.getAppMemoryPercent(pkgName);
                message.obj = i2;
                message.what = What.LINE_CHART_CHANGE_MEMORY;
                handler.sendMessage(message);
                break;
        }
    }
}
