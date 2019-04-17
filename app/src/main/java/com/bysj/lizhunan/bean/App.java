package com.bysj.lizhunan.bean;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;

/**
 * app实体类
 */
public class App {

    private String id;
    private String appName;
    private String processName;
    private String packageName;
    private String size;
    private Drawable image;
    private String version;
    private boolean isCtrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCtrl() {
        return isCtrl;
    }

    public void setCtrl(boolean ctrl) {
        isCtrl = ctrl;
    }
}
