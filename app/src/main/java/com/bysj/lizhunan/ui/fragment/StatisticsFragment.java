package com.bysj.lizhunan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseFragment;
import com.bysj.lizhunan.base.What;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.core.MemoryMonitor;
import com.bysj.lizhunan.unit.LineChartManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class StatisticsFragment extends BaseFragment {

    private static StatisticsFragment INSTANCE;

    private LineChart memoryLc, cpuLc, netLc;
    private LineChartManager memoryChartManager;
    private LineChartManager cpuChartManager;
    private LineChartManager netChartManager;

    public StatisticsFragment() {

    }

    public static StatisticsFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsFragment();
        }
        return INSTANCE;
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initView(View view) {
        memoryLc = $(view, R.id.memory_Chart);
        cpuLc = $(view, R.id.cpu_Chart);
        netLc = $(view, R.id.net_Chart);

        //初始化折线图
        memoryChartManager = new LineChartManager(memoryLc,getResources().getString(R.string.memory),getResources().getColor(R.color.colorTealPrimaryDark));
        cpuChartManager = new LineChartManager(cpuLc,getResources().getString(R.string.cpu),getResources().getColor(R.color.colorTealPrimaryDark));
        netChartManager = new LineChartManager(netLc,getResources().getString(R.string.net),getResources().getColor(R.color.colorTealPrimaryDark));
        memoryChartManager.setYAxis(100, 0, 10);
        cpuChartManager.setYAxis(100, 0, 10);
        netChartManager.setYAxis(100, 0, 10);
    }

    @Override
    protected void doBusiness(Context mContext, Activity activity) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    int i = MemoryMonitor.getMemoryPercent();
                    Log.d("tat", "d::" + i);
                    baseHandler.obtainMessage(What.LINE_CHART_CHANGE,i);
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void widgetClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void handleMessage(Message message) {

        switch (message.what){
            case What.LINE_CHART_CHANGE:
                memoryChartManager.addEntry((Integer) message.obj);
                break;
        }
    }
}
