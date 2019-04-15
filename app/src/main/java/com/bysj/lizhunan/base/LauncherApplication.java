package com.bysj.lizhunan.base;

import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import com.bysj.lizhunan.core.CoreControlReceiver;

public class LauncherApplication extends Application {

    private static Context context;
    public static DevicePolicyManager mDPM;
    public static ComponentName mComponentName;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, CoreControlReceiver.class);

        context = this;

    }
}
