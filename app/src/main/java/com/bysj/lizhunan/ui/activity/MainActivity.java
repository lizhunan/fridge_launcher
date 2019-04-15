package com.bysj.lizhunan.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.bysj.lizhunan.R;
import com.bysj.lizhunan.base.BaseActivity;
import com.bysj.lizhunan.ui.fragment.AppsFragment;
import com.bysj.lizhunan.ui.fragment.SettingFragment;
import com.bysj.lizhunan.ui.fragment.StatisticsFragment;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation ;
    private AppsFragment appsFragment;
    private StatisticsFragment statisticsFragment;
    private SettingFragment settingFragment;
    private Toolbar toolbar;
    private TextView titleTv;

    @Override
    protected void initParms(Bundle parms) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View view) {
        navigation = $(R.id.navigation);
        toolbar = $(R.id.toolbar);
        titleTv = $(R.id.title_tv);
        appsFragment = AppsFragment.newInstance();
        statisticsFragment = StatisticsFragment.newInstance();
        settingFragment = SettingFragment.newInstance();
        /*
         * 设置toolbar
         * */
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        titleTv.setText(getResources().getString(R.string.apps));
        setSupportActionBar(toolbar);
        switchFragment(appsFragment,R.id.content).commit();
    }

    @Override
    protected void doBusiness(Context mContext) {

    }

    @Override
    protected void widgetClick(View view) {

    }

    @Override
    protected void setListener() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void handleMessage(Message message, int what) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_apps:
                titleTv.setText(getResources().getString(R.string.apps));
                switchFragment(appsFragment,R.id.content).commit();
                return true;
            case R.id.navigation_statistics:
                titleTv.setText(getResources().getString(R.string.statistic));
                switchFragment(statisticsFragment,R.id.content).commit();
                return true;
            case R.id.navigation_setting:
                titleTv.setText(getResources().getString(R.string.setting));
                switchFragment(settingFragment,R.id.content).commit();
                return true;
        }
        return false;
    }
}
