package com.bysj.lizhunan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseFragment;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.core.MemoryMonitor;
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
    private LineDataSet lineDataSet;
    private LineData data;

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

        List<Entry> entries = new ArrayList<>();
        setLineChart(getResources().getString(R.string.memory), memoryLc, entries);
        setLineChart(getResources().getString(R.string.cpu), cpuLc, entries);
        setLineChart(getResources().getString(R.string.net), netLc, entries);

    }

    @Override
    protected void doBusiness(Context mContext, Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    float i = MemoryMonitor.getMemoryPercent();
                    Log.d("tat", "d::" + i);
                    memoryLc.setData(data);
                    Entry entry = new Entry(lineDataSet.getEntryCount(),  (float) (Math.random()) * 80);
                    data.addEntry(entry, 0);
                    //通知数据已经改变
                    data.notifyDataChanged();
                    memoryLc.notifyDataSetChanged();
                    //设置在曲线图中显示的最大数量
                    memoryLc.setVisibleXRangeMaximum(10);
                    //移到某个位置
                    memoryLc.moveViewToX(data.getEntryCount() - 5);
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

    private void setLineChart(String title, LineChart lineChart, List<Entry> entries) {
        lineChart.setNoDataText(getResources().getString(R.string.no_chart_line_data));
        lineDataSet = new LineDataSet(entries, title);
        data = new LineData(lineDataSet);
        lineChart.setData(data);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftYAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);
        rightYAxis.setEnabled(false);
        rightYAxis.setGranularity(1f);
        rightYAxis.setLabelCount(11, false);
        rightYAxis.setTextColor(Color.BLACK); //文字颜色
        rightYAxis.setGridColor(Color.WHITE); //网格线颜色
        rightYAxis.setAxisLineColor(getResources().getColor(R.color.colorTealPrimaryDark)); //Y轴颜色
        Legend legend = lineChart.getLegend();
        legend.setTextColor(getResources().getColor(R.color.colorTealPrimaryDark)); //设置Legend 文本颜色
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorTealPrimaryDark));
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(getResources().getColor(R.color.colorTealPrimaryDark));
    }

}
