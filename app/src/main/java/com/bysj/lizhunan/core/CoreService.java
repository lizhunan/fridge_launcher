package com.bysj.lizhunan.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bysj.lizhunan.R;

/**
 * 系统资源监测服务
 */
public class CoreService extends Service {

    private View floatView = null;
    private TextView descView;

    public CoreService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        floatView = getIconView();
        descView = floatView.findViewById(R.id.content);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private View getIconView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.float_view, null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
