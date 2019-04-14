package com.bysj.lizhunan.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.logging.Logger;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,BaseHandler.HandlerListener {

    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = false;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = false;
    /**
     * 横竖屏 true 竖屏，false 横屏
     **/
    private boolean isAllowScreenRoate = true;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 填充当前fragment
     */
    protected Fragment currentFragment = new Fragment();

    /**
     * handler
     */
    protected BaseHandler baseHandler;

    /**
     * 日志信息
     **/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
 /*
            * 设置activity传递的消息
            * */
            Bundle bundle = getIntent().getExtras();
            initParms(bundle);
            /*
            * 绑定当前activity布局
            * */
            mContextView = LayoutInflater.from(this)
                    .inflate(bindLayout(), null);
            /*
            * 全屏设置
            * */
            if (mAllowFullScreen) {
                this.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
            /*
            * 浸入式状态栏
            * */
            if (isSetStatusBar) {
                steepStatusBar();
            }
            /*
            * 设置布局
            * */
            setContentView(mContextView);
            /*
            * 是否允许旋转屏幕
            * */
            if (!isAllowScreenRoate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            baseHandler = new BaseHandler(this);
            baseHandler.setHandlerListener(this);
            initView(mContextView);
            setListener();
            doBusiness(this);
        } catch (Exception e) {

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (fastClick()) {
            widgetClick(view);
        }
    }

    /**
     * 初始化bundle参数
     *
     * @param parms parms
     */
    protected abstract void initParms(Bundle parms);

    /**
     * 绑定布局
     *
     * @return 布局ID
     */
    protected abstract int bindLayout();

    /**
     * 初始化控件
     *
     * @param view view
     */
    protected abstract void initView(final View view);

    /**
     * 业务操作
     *
     * @param mContext context
     */
    protected abstract void doBusiness(Context mContext);

    /**
     * View点击
     *
     * @param view view
     */
    protected abstract void widgetClick(View view);

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 设置toolbar
     * @param title 标题
     */
    protected void initToolBar(String title){

    }

    /**
     * 沉浸状态栏
     */
    private void steepStatusBar() {
        // 透明状态栏
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 防止过快点击
     *
     * @return true 正常点击，false 过快点击
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 页面跳转
     *
     * @param clz 当前activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 带参页面跳转
     *
     * @param clz    当前activity
     * @param bundle 参数集
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 带参返回页面
     *
     * @param cls         当前activity
     * @param bundle      参数集
     * @param requestCode 返回代码
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 绑定控件
     *
     * @param resId 控件ID
     * @param <T>   控件类型
     * @return 控件类型
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 切换fragment
     * 用于优化切换fragment时卡顿现象
     *
     * @param targetFragment 目标fragment
     * @param resId          fragment容器
     * @return FragmentTransaction
     */
    protected FragmentTransaction switchFragment(Fragment targetFragment, int resId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                fragmentTransaction.hide(currentFragment);
            }
            fragmentTransaction.add(resId, targetFragment, targetFragment.getClass().getName());
        } else {
            fragmentTransaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return fragmentTransaction;
    }

    public void setSetStatusBar(boolean setStatusBar) {
        isSetStatusBar = setStatusBar;
    }

    public void setmAllowFullScreen(boolean mAllowFullScreen) {
        this.mAllowFullScreen = mAllowFullScreen;
    }

    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

}
