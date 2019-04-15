package com.bysj.lizhunan.core;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.bysj.lizhunan.ui.activity.MainActivity;

public class CoreControlReceiver extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "启用了策略");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        showToast(context, "试图停用策略");
        return null;
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "停用了策略");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "改变了密码");

    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "密码尝试失败");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "密码尝试成功");
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        showToast(context, "密码过期");
    }

    void showToast(Context context, String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        super.onProfileProvisioningComplete(context, intent);
        DevicePolicyManager manager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = getComponentName(context);
        manager.setProfileName(componentName, "aaa");
        // Open the main screen
        Intent launch = new Intent(context, MainActivity.class);
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launch);
    }

    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), CoreControlReceiver.class);
    }
}
