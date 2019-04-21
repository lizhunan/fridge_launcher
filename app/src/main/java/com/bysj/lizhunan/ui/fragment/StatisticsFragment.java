package com.bysj.lizhunan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseFragment;
import com.bysj.lizhunan.base.Constants;
import com.bysj.lizhunan.base.What;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.bean.Used;
import com.bysj.lizhunan.core.CoreService;
import com.bysj.lizhunan.core.MemoryMonitor;
import com.bysj.lizhunan.presenter.AppsPresenter;
import com.bysj.lizhunan.widget.LineChartManager;
import com.bysj.lizhunan.core.LooperTask;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class StatisticsFragment extends BaseFragment implements SearchView.OnQueryTextListener, IGetData<List<App>> {

    private static StatisticsFragment INSTANCE;

    private LineChart memoryLc, cpuLc, netLc;
    private FloatingActionButton cleanMemoryBtn;
    private SearchView searchView;
    private ProgressBar progressBar;
    private LineChartManager memoryChartManager;
    private LineChartManager cpuChartManager;
    private LineChartManager netChartManager;
    private LooperTask task;
    private ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    private AppsPresenter appsPresenter;
    public static Handler mHandler;

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
        cleanMemoryBtn = $(view, R.id.clean_memory);
        searchView = $(view, R.id.app_search);
        progressBar = $(view, R.id.progress);

        //初始化searchView
        searchView.setQueryHint(getResources().getString(R.string.search_app_name));

        //初始化折线图
        memoryChartManager = new LineChartManager(memoryLc, getResources().getString(R.string.memory), getResources().getColor(R.color.colorTealPrimaryDark));
        cpuChartManager = new LineChartManager(cpuLc, getResources().getString(R.string.cpu), getResources().getColor(R.color.colorTealPrimaryDark));
        netChartManager = new LineChartManager(netLc, getResources().getString(R.string.net), getResources().getColor(R.color.colorTealPrimaryDark));
        memoryChartManager.setYAxis(100, 0, 10);
        cpuChartManager.setYAxis(100, 0, 10);
        netChartManager.setYAxis(100, 0, 10);

    }

    @Override
    protected void doBusiness(Context mContext, Activity activity) {
        appsPresenter = new AppsPresenter(this, handler);
        mHandler = handler;
        Intent intent = new Intent(getActivity(), CoreService.class);
        intent.setAction(Constants.START_CORE_SERVICE);
        getActivity().startService(intent);
    }

    @Override
    protected void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.clean_memory:
                MemoryMonitor.cleanMemory();
                break;
        }
    }

    @Override
    protected void setListener() {
        cleanMemoryBtn.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void handleMessage(Message message, int what) {

        switch (message.what) {
            case What.USED_DATA_CHANGE:
                Log.d("handleMessage:", "LINE_CHART_CHANGE:" + message.obj);
                memoryChartManager.addEntry((Integer) message.obj);
                break;
            case What.PROCESS_USED_DATA_CHANGE:
                Log.d("handleMessage:", "LINE_CHART_CHANGE:" + message.obj);
                memoryChartManager.addEntry((Integer) message.obj);
                break;
            case What.LINE_CHART_CHANGE:
                Log.d("handleMessage:", "LINE_CHART_CHANGE:" + message.obj);
                Used used = (Used)message.obj;
                memoryChartManager.addEntry(used.getMemoryPer());
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Log.d("onQueryTextSubmit:", "onQueryTextSubmit:" + query);
            appsPresenter.getData(3, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.equals("")) {
            //输入框里如果什么都没有，默认为查看总数据
            Intent intent = new Intent(getActivity(), CoreService.class);
            intent.setAction(Constants.START_CORE_SERVICE);
            getActivity().startService(intent);
        }
        return false;
    }

    @Override
    public void showSuccess(List<App> appInfo) {
        Intent intent = new Intent(getActivity(), CoreService.class);
        intent.putExtra(Constants.START_CHANGE_CORE_SERVICE, appInfo.get(0).getPackageName());
        intent.setAction(Constants.START_CHANGE_CORE_SERVICE);
        getActivity().startService(intent);
    }

    @Override
    public void showFail() {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hintProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshUI() {

    }
}
