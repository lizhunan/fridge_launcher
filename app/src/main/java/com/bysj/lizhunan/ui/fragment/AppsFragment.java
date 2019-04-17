package com.bysj.lizhunan.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseFragment;
import com.bysj.lizhunan.bean.App;
import com.bysj.lizhunan.presenter.AppsPresenter;
import com.bysj.lizhunan.ui.fragment.adapter.AppListAdapter;

import java.util.List;


public class AppsFragment extends BaseFragment implements IGetData<List<App>>{

    public static AppsFragment INSTANCE;

    private RecyclerView appsList;
    private ProgressBar progressBar;
    private AppsPresenter appsPresenter;
    private AppListAdapter appListAdapter;

    public AppsFragment() {

    }


    public static AppsFragment newInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AppsFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_apps;
    }

    @Override
    protected void initView(View view) {
        appsList = $(view,R.id.apps_list);
        progressBar = $(view,R.id.progress);
        appsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void doBusiness(Context mContext, Activity activity) {
        appsPresenter = new AppsPresenter(this,handler);
        appListAdapter = new AppListAdapter(getActivity());
        appsList.setAdapter(appListAdapter);
        appsPresenter.getData(1,null);
    }

    @Override
    protected void widgetClick(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void handleMessage(Message message, int what) {

    }


    @Override
    public void showSuccess(List<App> appInfos) {
        for (int i = 0; i < appInfos.size(); i++) {
            Log.d("aa",":"+appInfos.get(i).getAppName());
        }

        appListAdapter.updateListView(appInfos);
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
