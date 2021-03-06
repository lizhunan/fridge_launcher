package com.bysj.lizhunan.core;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseHandler;
import com.bysj.lizhunan.base.Constants;
import com.bysj.lizhunan.base.What;
import com.bysj.lizhunan.bean.Used;
import com.bysj.lizhunan.http.RetrofitUtils;
import com.bysj.lizhunan.ui.fragment.StatisticsFragment;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 系统资源监测服务
 */
public class CoreService extends Service implements View.OnTouchListener {

    private View floatView = null;
    private static TextView descView;
    private ConstraintLayout layout;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private int statusBarHeight = -1;
    private int lastX = 0;
    private int lastY = 0;
    private LooperTask task;
    private ScheduledExecutorService pool;
    private CoreHandler handler = new CoreHandler();
    private static SharedPreferences sp;
    private static String pkgName;

    public CoreService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        floatView = getIconView();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        descView = floatView.findViewById(R.id.content);
        if (sp.getBoolean(getString(R.string.pref_key_is_float_settings), true)) {
            createFloatView();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String state = Constants.START_CORE_SERVICE;
        if (intent != null) {
            state = intent.getAction();
        }
        switch (state) {
            case Constants.START_CORE_SERVICE:
                if (pool == null) {
                    pool = Executors.newScheduledThreadPool(1);
                }
                task = LooperTask.getINSTANCE(handler);
                pkgName = "所有应用";
                task.setSomething(pkgName);
                task.setWhat(What.USED_DATA_CHANGE);
                pool.scheduleAtFixedRate(task, 0, 3 * 1000, TimeUnit.MILLISECONDS);
                break;
            case Constants.START_CHANGE_CORE_SERVICE:
                if (pool == null) {
                    pool = Executors.newScheduledThreadPool(1);
                }
                pkgName = intent.getStringExtra(Constants.START_CHANGE_CORE_SERVICE);
                task.setSomething(pkgName);
                task.setWhat(What.PROCESS_USED_DATA_CHANGE);
                pool.scheduleAtFixedRate(task, 0, 3 * 1000, TimeUnit.MILLISECONDS);
                break;
            case Constants.STOP_CORE_SERVICE:
                break;
            case Constants.SHOW_FLOAT_VIEW:
                break;
            case Constants.HINT_FLOAT_VIEW:
                break;

        }

        return super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
    }

    private View getIconView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.float_view, null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 创建悬浮窗
     */
    private void createFloatView() {
        params = new WindowManager.LayoutParams();//初始化params
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//设置type，用于其他程序窗口之上
        params.format = PixelFormat.RGBA_8888;//设置效果半透明
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不可聚焦及不可使用按钮对悬浮窗进行操控

        //初始化窗口停靠位置
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;

        //窗口大小
        params.width = 300;
        params.height = 300;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        layout = (ConstraintLayout) inflater.inflate(R.layout.float_view, null); //获取浮动窗口视图所在布局.
        windowManager.addView(layout, params);//添加layout

        layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED); //主动计算出当前View的宽高信息.

        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        descView = layout.findViewById(R.id.content);
        descView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("onTouch", "::" + motionEvent.getAction());

        switch (view.getId()) {

            case R.id.content:
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) motionEvent.getRawX();
                        lastY = (int) motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取移动后的位置
                        lastX = (int) motionEvent.getRawX();
                        lastY = (int) motionEvent.getRawY();
                        params.x = lastX - 150;
                        params.y = lastY - 150 - statusBarHeight;
                        windowManager.updateViewLayout(layout, params);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        //用descView检查悬浮窗是否存在
        if (descView != null) {
            windowManager.removeView(layout);
        }
        super.onDestroy();
    }


    public static class CoreHandler extends BaseHandler {
        JSONObject jsonObject = new JSONObject();
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg, int what) {
            Used used = (Used) msg.obj;
            try {
                Message message = StatisticsFragment.mHandler.obtainMessage();
                switch (msg.what) {
                    case What.USED_DATA_CHANGE:
                        Log.d("handleMessage:", "LINE_CHART_CHANGE:" + msg.obj);
                        if (used.getCpuPer() > 0 && used.getCpuPer() < 1) {
                            used.setCpuPer(1.0);
                        }
                        if (used.getCurrNet() >= 1048576d) {
                            used.setCurrNet((int) (used.getCurrNet() / 1048576d));
                        } else {
                            used.setCurrNet((int) (used.getCurrNet() / 1024d));
                        }
                        descView.setText("总内存：" + MemoryMonitor.getTotalSize() + "\n" +
                                "可使用内存：" + MemoryMonitor.getAvailableMemory() / 1024 + "\n" +
                                "已使用内存：" + used.getMemoryUsed() + "\n" +
                                "当前内存使用百分比：" + used.getMemoryPer() + "%\n" + "" +
                                "当前CPU使用百分比" + used.getCpuPer() + "%\n" +
                                "当前网速" + used.getCurrNet());
                        jsonObject.put("memory",used.getMemoryPer());
                        jsonObject.put("cpu",used.getCpuPer());
                        message.what = What.LINE_CHART_CHANGE;
                        message.obj = used;
                        StatisticsFragment.mHandler.sendMessage(message);
                        break;
                    case What.PROCESS_USED_DATA_CHANGE:
                        Log.d("handleMessage:", "LINE_CHART_PROCESS_CHANGE:" + msg.obj);

                        descView.setText("总内存：" + MemoryMonitor.getTotalSize() + "\n" +
                                "可使用内存：" + MemoryMonitor.getAvailableMemory() / 1024 + "\n" +
                                "当前进程已使用内存：" + used.getMemoryUsed() + "\n" +
                                "当前内存使用百分比：" + used.getMemoryPer() + "%\n" +
                                "当前进程内存使用情况：" + used.getCurrMemory() + "%\n");
                        jsonObject.put("memory",used.getCurrMemory());
                        jsonObject.put("cpu",used.getCpuPer());
                        message.what = What.LINE_CHART_PROCESS_CHANGE;
                        message.obj = used;
                        StatisticsFragment.mHandler.sendMessage(message);
                        break;
                }
                if (sp.getBoolean("pref_key_catch_settings", false)) {

                    Call call = RetrofitUtils.getClient().newCall(RetrofitUtils.request(pkgName,jsonObject.toString()));
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class UploadThread extends Thread {
        @Override
        public void run() {
            Log.d("run", "run");
            //Map<String,String> map = new HashMap<>();
            //map.put("appName","");
            //map.put("info","");
            //RetrofitUtils.getInstance().getApiService().getBulletin(map);
        }
    }
}
