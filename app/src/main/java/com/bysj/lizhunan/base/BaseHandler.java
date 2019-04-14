package com.bysj.lizhunan.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 封装Handler
 */
public class BaseHandler extends Handler {

    protected WeakReference<BaseActivity> activityWeakReference;
    protected WeakReference<BaseFragment> fragmentWeakReference;
    private HandlerListener handlerListener;

    public BaseHandler() {
    }

    public BaseHandler(BaseActivity baseActivity) {
        this.activityWeakReference = new WeakReference<>(baseActivity);
    }

    public BaseHandler(BaseFragment baseFragment) {
        this.fragmentWeakReference = new WeakReference<>(baseFragment);
    }

    public void setHandlerListener(HandlerListener handlerListener) {
        this.handlerListener = handlerListener;
    }

    @Override
    public void handleMessage(Message msg) {
        if(handlerListener != null){
            handlerListener.handleMessage(msg);
        }
    }


    /**
     * handler回调接口
     */
    public interface HandlerListener {
        /**
         * 处理具体业务
         * @param message 信息
         */
        void handleMessage(Message message);
    }
}
