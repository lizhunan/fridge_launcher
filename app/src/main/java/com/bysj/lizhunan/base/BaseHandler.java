package com.bysj.lizhunan.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 封装Handler
 */
public abstract class BaseHandler extends Handler {

    protected WeakReference<BaseActivity> activityWeakReference;
    protected WeakReference<BaseFragment> fragmentWeakReference;

    public BaseHandler() {
    }

    public BaseHandler(BaseActivity baseActivity) {
        this.activityWeakReference = new WeakReference<>(baseActivity);
    }

    public BaseHandler(BaseFragment baseFragment) {
        this.fragmentWeakReference = new WeakReference<>(baseFragment);
    }

    @Override
    public void handleMessage(Message msg) {
        if (activityWeakReference == null || activityWeakReference.get() == null || activityWeakReference.get().isFinishing()) {
            // 确认Activity是否不可用
            handleMessage(msg, What.ACTIVITY_GONE);
        } else if (fragmentWeakReference == null || fragmentWeakReference.get() == null || fragmentWeakReference.get().isRemoving()) {
            //确认判断Fragment不可用
            handleMessage(msg, What.ACTIVITY_GONE);
        } else {
            handleMessage(msg, msg.what);
        }
    }

    /**
     * 实现抽象方法，处理具体业务
     * @param msg 信息
     * @param what What.class
     */
    public abstract void handleMessage(Message msg, int what);

}
